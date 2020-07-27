package net.hydrogen2oxygen.hyperselenium.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StringUtils;

import java.util.List;

public class HyperWebDriver {

    private WebDriver driver;

    private HyperWebDriver() {
        driver = new ChromeDriver();
    }

    public static HyperWebDriver build() {
        return new HyperWebDriver();
    }

    public HyperWebDriver openPage(String url) {
        driver.get(url);
        return this;
    }

    public HyperWebDriver insertText(String id, String text) {
        driver.findElement(By.id(id)).sendKeys(text);
        return this;
    }

    public HyperWebDriver insertTextByName(String id, String text) {
        driver.findElement(By.name(id)).sendKeys(text);
        return this;
    }

    public HyperWebDriver click(String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }

    public HyperWebDriver clickName(String id) {
        driver.findElement(By.name(id)).click();
        return this;
    }

    public HyperWebDriver clickTagContainingText(String tag, String text) {

        List<WebElement> list = driver.findElements(By.tagName(tag));

        boolean clickPerformed = false;

        for (WebElement element : list) {

            String elementText = element.getText().trim();

            if (StringUtils.isEmpty(elementText)) {
                continue;
            }

            if (text.equals(elementText)) {
                element.click();
                clickPerformed = true;
                break;
            }
        }

        return this;
    }

    public HyperWebDriver selectOption(String id, String optionText) {

        Select options = new Select(driver.findElement(By.id(id)));
        options.selectByVisibleText(optionText);

        return this;
    }

    public HyperWebDriver selectOption(String id, int index) {

        Select options = new Select(driver.findElement(By.id(id)));
        options.selectByIndex(index);

        return this;
    }

    public HyperWebDriver switchToFrame(String id) {
        driver.switchTo().frame(id);
        return this;
    }

    public HyperWebDriver switchToParentFrame() {
        driver.switchTo().parentFrame();
        return this;
    }

    public void close() {
        driver.close();
    }

    public HyperWebDriver waitMillis(int millis) throws InterruptedException {
        Thread.sleep(millis);
        return this;
    }

    public HyperWebDriver waitForElement(String id, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By
                .id(id))));

        return this;
    }

    public HyperWebDriver waitForTag(String tagName, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By
                .tagName(tagName))));

        return this;
    }
}
