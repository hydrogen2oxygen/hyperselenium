package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.domain.ICommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class HyperseleniumService {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    private Map<String, ICommand> commands = new HashMap<>();

    @PostConstruct
    public void initService() throws Exception{

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().whitelistPackages("net.hydrogen2oxygen")
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
        return command.executeCommand(driver, line.substring(line.indexOf(" ")).trim().split(" "));
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
