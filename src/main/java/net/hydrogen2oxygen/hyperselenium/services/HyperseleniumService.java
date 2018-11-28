package net.hydrogen2oxygen.hyperselenium.services;

import io.github.classgraph.*;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.domain.ICommand;
import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class HyperseleniumService {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    private Map<String, WebSite> websites = new HashMap<>();
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

    public WebSite openWebsite(WebSite webSite) throws Exception {

        // TODO make this dynamic for different OS
        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URI newUri = builder.build().toUri();

        String uuid = UUID.randomUUID().toString();
        webSite.setHyperseleniumOriginUrl(newUri.toString().replaceAll("openWebsite", ""));
        webSite.setUuid(uuid);
        webSite.setWebDriver(HyperWebDriver.build());
        webSite.getWebDriver().openPage(webSite.getUrl()).waitMillis(500).injectEditor(webSite);
        websites.put(uuid, webSite);

        return webSite;
    }

    public WebSite closeWebsite(String uuid) {

        WebSite webSite = websites.get(uuid);

        if (webSite.getWebDriver() != null) {
            webSite.getWebDriver().close();
            webSite.setWebDriver(null);
        }

        return webSite;
    }

    public void closeAllDrivers() {

        for (String uuid : websites.keySet()) {
            WebSite webSite = websites.get(uuid);

            try {
                webSite.getWebDriver().close();
                webSite.getWebDriver().getDriver().close();
            } catch (Exception e) {

            }
        }
    }
}