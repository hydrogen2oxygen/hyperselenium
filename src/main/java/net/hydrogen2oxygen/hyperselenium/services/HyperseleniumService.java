package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.domain.*;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.Param;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Service
public class HyperseleniumService {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private StatusService statusService;

    private Map<String, ICommand> commands = new HashMap<>();

    private ParamsUtility paramsUtility = new ParamsUtility();

    @PostConstruct
    public void initService() throws Exception{

        scanForCommands("net.hydrogen2oxygen");
    }

    /**
     * Scans for HyperseleniumCommand annotated commands. Be careful, you can override an existing command with this method.
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
            Constructor constuctor = objClass.getConstructor();
            ICommand command = (ICommand) constuctor.newInstance();
            commands.put(command.getCommandName(), command);
            System.out.println("Command " + command.getCommandName() + " registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandResult executeCommandLine(final HyperWebDriver driver, String line, final ProtocolLine protocolLine) {

        CommandResult result = new CommandResult();

        String [] parts = line.split(" ");
        ICommand command = commands.get(parts[0]);

        if (command == null) {
            result.setSuccess(false);
            result.setMessage(String.format("Command %s not found!", parts[0]));
            return result;
        }

        String [] parameters = {};

        if (line.contains(" ")) {
            parameters = paramsUtility.getParamsFromCommandLine(line);
        }

        try {
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

    public void executeScenario(Scenario scenario) {

        if (scenario.getDriver() == null) {
            scenario.setDriver(getNewHyperWebDriver());
        }

        addNewProtocol(scenario);

        // Execute the main script
        executeScript(scenario);
    }

    public void addNewProtocol(final Scenario scenario) {

        Protocol protocol = new Protocol();
        scenario.setProtocol(protocol);

        Script script = scenario.getScript();

        int lineCounter = -1;

        for (String line : script.getLines()) {

            lineCounter++;
            ProtocolLine protocolLine = new ProtocolLine();
            protocolLine.setLine(line);
            protocolLine.setLineNumber(lineCounter+1);

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
    }

    public HyperWebDriver getNewHyperWebDriver() {

        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");

        HyperWebDriver driver = HyperWebDriver.build();
        driver.setScreenshotsPath(dataBaseService.getSetting(DataBaseService.SCREENSHOTS_PATH));

        return driver;
    }

    public void executeScript(Scenario scenario) {

        Script script = scenario.getScript();
        Protocol protocol = scenario.getProtocol();
        protocol.setStatus("RUNNING");
        statusService.addScenarioUpdate(scenario);

        int lineCount = -1;

        for (String line : script.getLines()) {

            lineCount++;

            ProtocolLine protocolLine = protocol.getLines().get(lineCount);

            if (line.trim().isEmpty()) {
                protocolLine.setStatus("PASS");
                continue;
            }

            if (line.startsWith("    ")) {
                protocolLine.setStatus("RUNNING");

                statusService.addScenarioUpdate(scenario);

                if (line.contains("    open ") && scenario.getDriver().isClosed()) {
                    // FIXME search for a better solution
                    scenario.setDriver(getNewHyperWebDriver());
                }

                CommandResult result = executeCommandLine(scenario.getDriver(), line.trim(), protocolLine);

                protocolLine.setResult(result.getMessage());

                if ("true".equals(dataBaseService.getSetting(DataBaseService.STOP_WHEN_ERROR_OCCURS))
                    && !result.getSuccess()) {

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

        protocol.setStatus("FINISHED");

        statusService.addScenarioUpdate(scenario);
        statusService.sendStatus();
    }


    public void closeAllDrivers() {

        // FIXME
        /*for (String uuid : websites.keySet()) {
            WebSite webSite = websites.get(uuid);

            try {
                webSite.getWebDriver().close();
                webSite.getWebDriver().getDriver().close();
            } catch (Exception e) {

            }
        }*/
    }

}
