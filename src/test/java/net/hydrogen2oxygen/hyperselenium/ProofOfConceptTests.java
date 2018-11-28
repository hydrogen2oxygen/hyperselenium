package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class ProofOfConceptTests {

    @Value("${selenium.driver.directory}")
    private String seleniumDriverDirectory;

    @Before
    public void setup() {
        if (seleniumDriverDirectory == null) {
            seleniumDriverDirectory = "../SELENIUMDRIVERs/";
        }
    }

    @Test
    public void test() throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "chromedriver.exe");
        HyperWebDriver driver = HyperWebDriver.build();
        driver.openPage("https://google.de");
        //driver.injectEditor();
        driver.close();
    }
}