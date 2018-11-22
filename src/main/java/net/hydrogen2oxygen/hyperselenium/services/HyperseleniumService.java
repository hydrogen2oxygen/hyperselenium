package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class HyperseleniumService {

    @Value( "${selenium.driver.directory}" )
    private String seleniumDriverDirectory;

    private Map<String,WebSite> websites = new HashMap<>();

    public WebSite openWebsite(WebSite webSite) throws Exception {

        // TODO make this dynamic for different OS
        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URI newUri = builder.build().toUri();

        String uuid = UUID.randomUUID().toString();
        webSite.setHyperseleniumOriginUrl(newUri.toString().replaceAll("openWebsite",""));
        webSite.setUuid(uuid);
        webSite.setWebDriver(HyperWebDriver.build());
        webSite.getWebDriver().openPage(webSite.getUrl()).waitMillis(500).injectEditor(webSite);
        websites.put(uuid,webSite);

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

        for(String uuid : websites.keySet()) {
            WebSite webSite = websites.get(uuid);

            try {
                webSite.getWebDriver().close();
                webSite.getWebDriver().getDriver().close();
            } catch (Exception e) {

            }
        }
    }
}