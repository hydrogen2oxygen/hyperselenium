package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.junit.Test;

import java.io.IOException;

public class ProofOfConceptTests {

    @Test
    public void test() throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\DEVELOPMENT\\devtools\\SELENIUMDRIVERs\\chromedriver.exe");
        HyperWebDriver driver = HyperWebDriver.build();
        driver.openPage("https://google.de");
        //driver.injectEditor();
        driver.close();
    }
}
