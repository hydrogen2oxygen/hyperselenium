package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class HyperseleniumService {

    @Value( "${selenium.driver.directory}" )
    private String seleniumDriverDirectory;

    private Long idCount = 0L;
    private Map<Long,WebSite> websites = new HashMap<>();

    public WebSite openWebsite(WebSite webSite) throws IOException {

        // TODO make this dynamic for different OS
        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        URI newUri = builder.build().toUri();

        webSite.setHyperseleniumOriginUrl(newUri.toString().replaceAll("openWebsite",""));
        webSite.setId(idCount);
        webSite.setWebDriver(HyperWebDriver.build());
        websites.put(idCount,webSite);

        webSite.getWebDriver().openPage(webSite.getUrl()).injectEditor(webSite);

        return webSite;
    }

    public WebSite closeWebsite(Long id) {

        WebSite webSite = websites.get(id);

        if (webSite.getWebDriver() != null) {
            webSite.getWebDriver().close();
            webSite.setWebDriver(null);
        }

        return webSite;
    }
}