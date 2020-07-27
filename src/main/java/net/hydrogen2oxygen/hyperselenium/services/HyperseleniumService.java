package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.domain.*;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Service
public class HyperseleniumService {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    private Map<String, ICommand> commands = new HashMap<>();

    @PostConstruct
    public void initService() throws Exception{

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("net.hydrogen2oxygen")
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

    public CommandResult executeCommandLine(HyperWebDriver driver, String line) {

        String [] parts = line.split(" ");
        ICommand command = commands.get(parts[0]);
        String [] parameters = {};

        if (line.contains(" ")) {
            parameters = line.substring(line.indexOf(" ")).trim().split(" ");
        }

        try {
            return command.executeCommand(driver, parameters);
        } catch (Exception e) {
            CommandResult result = new CommandResult();
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public void executeScenario(Scenario scenario) {

        if (scenario.getDriver() == null) {
            scenario.setDriver(getNewHyperWebDriver());
        }

        // Execute the main script
        executeScript(scenario.getScript(), scenario.getDriver());
    }

    public HyperWebDriver getNewHyperWebDriver() {

        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");

        return HyperWebDriver.build();
    }

    public void executeScript(Script script, HyperWebDriver driver) {

        for (String line : script.getLines()) {

            if (line.trim().isEmpty()) continue;

            if (line.startsWith("    ")) {
                String[] parts = line.trim().split(" ");
                System.out.print(line + " - ");
                CommandResult result = executeCommandLine(driver, line.trim());
                System.out.println(result.getMessage());
            } else {
                System.out.println(line);
            }
        }
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
