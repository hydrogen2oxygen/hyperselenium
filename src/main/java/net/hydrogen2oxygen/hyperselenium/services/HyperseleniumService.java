package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.domain.*;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class HyperseleniumService {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    private Map<String, ICommand> commands = new HashMap<>();

    @Autowired
    private StatusService statusService;

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

        String [] parts = line.split(" ");
        ICommand command = commands.get(parts[0]);
        String [] parameters = {};

        if (line.contains(" ")) {
            parameters = line.substring(line.indexOf(" ")).trim().split(" ");
        }

        try {
            CommandResult result = command.executeCommand(driver, parameters);
            protocolLine.setStatus("SUCCESS");
            return result;
        } catch (Exception e) {
            CommandResult result = new CommandResult();
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

    private void addNewProtocol(Scenario scenario) {

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

        return HyperWebDriver.build();
    }

    public void executeScript(Scenario scenario) {

        Script script = scenario.getScript();
        HyperWebDriver driver = scenario.getDriver();
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

                CommandResult result = executeCommandLine(driver, line.trim(), protocolLine);

                protocolLine.setResult(result.getMessage());

                statusService.addScenarioUpdate(scenario);
            } else {
                protocolLine.setStatus("PASS");
            }
        }

        protocol.setStatus("FINISHED");

        statusService.addScenarioUpdate(scenario);
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
