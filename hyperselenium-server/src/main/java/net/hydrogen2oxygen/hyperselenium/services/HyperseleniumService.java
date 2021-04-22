package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.domain.*;
import net.hydrogen2oxygen.hyperselenium.exceptions.HyperWebDriverException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

@Service
public class HyperseleniumService {

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private StatusService statusService;

    private List<String> scenariosToStop = new ArrayList<>();

    private Map<String, ICommand> commands = new HashMap<>();

    private List<Scenario> runningScenarios = new ArrayList<>();

    private ParamsUtility paramsUtility = new ParamsUtility();

    @PostConstruct
    public void initService() throws Exception {

        scanForCommands("net.hydrogen2oxygen");
    }

    @PreDestroy
    public void shutdown() {
        closeAllDrivers();

    }

    /**
     * Scans for HyperseleniumCommand annotated commands. Be careful, you can override an existing command with this method.
     *
     * @param packages
     */
    public void scanForCommands(String packages) {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packages)
                .scan()) {
            ClassInfoList checked = scanResult.getClassesWithAnnotation(HyperseleniumCommand.class.getCanonicalName());

            checked.forEach(classInfo -> {
                registerCommand(classInfo.getName());
            });
        }
    }

    private void registerCommand(String commandClassFullName) {
        try {
            Class objClass = Class.forName(commandClassFullName);
            Constructor constructor = objClass.getConstructor();
            ICommand command = (ICommand) constructor.newInstance();
            commands.put(command.getCommandName(), command);
            System.out.println("Command " + command.getCommandName() + " registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandResult executeCommandLine(Scenario scenario, String line, final ProtocolLine protocolLine) {

        HyperWebDriver driver = scenario.getDriver();
        CommandResult result = new CommandResult();

        line = line.trim();

        // Extract Variables
        if (line.startsWith("#") && line.contains("=")) {
            String keyValue[] = paramsUtility.extractKeyValue(line);
            scenario.getVariables().put(keyValue[0], keyValue[1]);
            result.setSuccess(true);
            result.setMessage("Variable " + line.trim() + " set successfully!");
            protocolLine.setStatus("SUCCESS");
            return result;
        }

        String commandName = line.split(" ")[0];
        ICommand command = commands.get(commandName);

        if (command == null) {
            result.setSuccess(false);
            result.setMessage(String.format("Command %s not found!", commandName));
            return result;
        }

        String[] parameters = {};

        if (line.contains(" ")) {
            parameters = paramsUtility.getParamsFromCommandLine(line);
        }

        try {
            parameters = paramsUtility.replaceVariablesInParameters(parameters, scenario.getVariables());
            result = command.executeCommand(driver, parameters);
            protocolLine.setStatus("SUCCESS");
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            protocolLine.setStatus("ERROR");
            return result;
        }
    }

    public void executeScenario(Scenario scenario, Integer lineNumber) {

        scenario.getVariables().putAll(paramsUtility.extractVariables(scenario));
        scenario.setMissingVariables(paramsUtility.extractMissingVariables(scenario));

        if (scenario.getDriver() == null) {
            try {
                scenario.setDriver(getNewHyperWebDriver());
            } catch (HyperWebDriverException e) {
                e.printStackTrace();
            }
        }

        scenario.setProtocol(addNewProtocol(scenario.getScript()));
        runningScenarios.add(scenario);

        // Execute the main script
        executeScript(scenario, null, null, lineNumber);
    }

    public void executeScenario(Scenario scenario) {

        executeScenario(scenario, null);
    }

    public Protocol addNewProtocol(Script script) {

        Protocol protocol = new Protocol();
        protocol.setScriptName(script.getName());

        int lineCounter = -1;

        for (String line : script.getLines()) {

            lineCounter++;
            ProtocolLine protocolLine = new ProtocolLine();
            protocolLine.setLine(line);
            protocolLine.setLineNumber(lineCounter + 1);

            if (line.startsWith("    ")) {
                protocolLine.setType("CODE");
            } else {
                if (line.startsWith("#")) {
                    protocolLine.setType("HEADER");
                } else {
                    protocolLine.setType("TEXT");
                }
            }

            protocol.getLines().add(protocolLine);
        }

        return protocol;
    }

    public HyperWebDriver getNewHyperWebDriver() throws HyperWebDriverException {

        HyperWebDriver driver = new HyperWebDriver(
            dataBaseService.getSetting(DataBaseService.SELENIUM_DRIVER_TYPE),
            dataBaseService.getSetting(DataBaseService.SELENIUM_GRID_REMOTE_URL),
            dataBaseService.getSetting(DataBaseService.SELENIUM_DRIVER_DIRECTORY)
        );
        driver.setScreenshotsPath(dataBaseService.getSetting(DataBaseService.SCREENSHOTS_PATH));

        return driver;
    }

    public void executeScript(Scenario scenario, Script script, ProtocolLine mainProtocolLine, Integer lineNumber) {

        Protocol protocol = scenario.getProtocol();
        boolean mainScript = true;

        // Scenario delivers the main script
        if (script == null) {
            script = scenario.getScript();
            protocol.setStatus("RUNNING");
        } else {
            // Sub-Script
            mainScript = false;
            protocol = addNewProtocol(script);
            mainProtocolLine.setSubScriptProtocol(protocol);
        }

        statusService.addScenarioUpdate(scenario);

        if (lineNumber != null) {
            lineNumber--;
        }

        int lineCount = -1;

        for (String line : script.getLines()) {

            lineCount++;

            if (lineNumber != null && lineCount < lineNumber) {
                continue;
            }

            if (isStopScenario(scenario.getName())) {
                protocol.setStatus("STOPPED");
                statusService.addScenarioUpdate(scenario);
                statusService.sendStatus();
                return;
            }

            ProtocolLine protocolLine = protocol.getLines().get(lineCount);

            if ("true".equals(dataBaseService.getSetting("breakpointsActive")) && script.getBreakpoints().contains(lineCount + 1)) {
                protocol.setStatus("STOPPED");
                protocolLine.setStatus("STOPPED");
                statusService.addScenarioUpdate(scenario);
                statusService.sendStatus();
                return;
            }

            if (line.trim().isEmpty()) {
                protocolLine.setStatus("PASS");
                continue;
            }

            if (line.startsWith("    ")) {
                protocolLine.setStatus("RUNNING");

                statusService.addScenarioUpdate(scenario);

                if (line.contains("    open ") && scenario.getDriver().isClosed()) {

                    try {
                        scenario.setDriver(getNewHyperWebDriver());
                    } catch (HyperWebDriverException e) {
                        protocol.setStatus("STOPPED");
                        return;
                    }
                }

                CommandResult result = executeCommandLine(scenario, line, protocolLine);

                // Special commands run outside a command object or are
                // delegated to other services
                if (result.getSpecialCommand() != null) {

                    String[] parameters = paramsUtility.getParamsFromCommandLine(line);

                    if ("run".equals(result.getSpecialCommand())) {
                        runScript(scenario, protocolLine, result, parameters[0]);
                    }

                    if ("if".equals(result.getSpecialCommand())) {
                        // TODO split it into first command and second command
                    }
                }

                protocolLine.setResult(result.getMessage());

                if ("true".equals(dataBaseService.getSetting(DataBaseService.STOP_WHEN_ERROR_OCCURS))
                        && !result.getSuccess()) {

                    if (scenario.getDriver().getDriverType().name().startsWith("REMOTE")) {
                        scenario.getDriver().close();
                    }

                    protocolLine.setStatus("STOPPED");
                    protocol.setStatus("STOPPED");
                    statusService.addScenarioUpdate(scenario);
                    statusService.sendStatus();
                    return;
                } else {
                    statusService.addScenarioUpdate(scenario);
                }
            } else {
                protocolLine.setStatus("PASS");
            }
        }

        // Remote drivers will always stop the current driver, else the remote selenium crash
        if (scenario.getDriver().getDriverType().name().startsWith("REMOTE")) {
            scenario.getDriver().close();
        }

        protocol.setStatus("FINISHED");

        statusService.addScenarioUpdate(scenario);
        statusService.sendStatus();
        runningScenarios.remove(scenario);
    }

    private void runScript(Scenario scenario, ProtocolLine protocolLine, CommandResult result, String parameter) {
        try {
            Scenario subScenario = dataBaseService.getScenarioByName(parameter);
            executeScript(scenario, subScenario.getScript(), protocolLine, 0);
            result.setSuccess(true);
            result.setMessage("Script " + subScenario.getScript().getName() + " executed successfully!");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            protocolLine.setStatus("ERROR");
        }
    }

    private boolean isStopScenario(String name) {

        boolean found = false;

        for (String scenarioName : scenariosToStop) {

            if (name.equals(scenarioName)) {
                found = true;
                break;
            }
        }

        if (found) {
            scenariosToStop.remove(name);
            return true;
        }

        return false;
    }


    public void closeAllDrivers() {

        System.out.println("closeAllDrivers called");

        for (Scenario scenario : runningScenarios) {

            if (scenario.getDriver() != null) {
                try {
                    scenario.getDriver().close();
                } finally {
                    scenario.setProtocol(null);
                    scenario.setDriver(null);
                }
            }
        }

        for (Scenario scenario : statusService.getServiceStatus().getScenarios()) {
            if (scenario.getDriver() != null) {
                try {
                    scenario.getDriver().close();
                } finally {
                    scenario.setProtocol(null);
                    scenario.setDriver(null);
                }
            }
        }

        try {
            // TODO check if Windows
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            // TODO check if Linux ...
        } catch (Exception e) {
        }
    }

    public void stopScenario(String name) {

        if (!scenariosToStop.contains(name)) {
            scenariosToStop.add(name);
        }
    }

    public void closeScenario(Scenario scenario) {

        Scenario scenarioToClose = null;

        for (Scenario s : runningScenarios) {
            if (scenario.getName().equals(s.getName())) {
                scenarioToClose = s;
                break;
            }
        }

        if (scenarioToClose != null) {
            scenarioToClose.getDriver().close();
            runningScenarios.remove(scenario);
        }
    }

    public List<Command> getCommands() {

        List<Command> commandList = new ArrayList<>();

        for (String name : commands.keySet()) {

            commandList.add(new Command(name, null));
        }

        Collections.sort(commandList, new Comparator<Command>() {
            @Override
            public int compare(Command o1, Command o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return commandList;
    }

    public Scenario updateBreakpoint(String name, Integer lineNumber) throws IOException {

        Scenario scenario = statusService.getRunningScenario(name);

        if (scenario == null) return null;

        if (scenario.getScript().getBreakpoints().contains(lineNumber)) {
            scenario.getScript().getBreakpoints().remove(lineNumber);
        } else {
            scenario.getScript().getBreakpoints().add(lineNumber);
        }

        return scenario;
    }
}
