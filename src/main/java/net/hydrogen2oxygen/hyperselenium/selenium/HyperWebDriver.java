package net.hydrogen2oxygen.hyperselenium.selenium;

import lombok.Getter;
import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

public class HyperWebDriver {

    @Getter
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

    public HyperWebDriver click(String id) {
        driver.findElement(By.id(id)).click();
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

        if (clickPerformed) {
            System.out.println(String.format("Click on hyperlink with text [%s] performed!", text));
        } else {
            System.err.println(String.format("Unable to click on hyperlink with text [%s] performed!", text));
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

    public HyperWebDriver injectEditor(WebSite webSite) throws IOException {

        try {
            String script = FileUtils.readFileToString(new ClassPathResource("editor.js").getFile(), "UTF-8");
            script = script.replaceAll("//SETVARIABLES", "websiteUUID = " + webSite.getUuid() + ";\nbaseUrl = '" + webSite.getHyperseleniumOriginUrl() + "';");

            ((JavascriptExecutor) driver)
                    .executeScript(String.format("var s=window.document.createElement('script');s.innerHTML='%s';window.document.head.appendChild(s);", StringEscapeUtils.escapeJavaScript(script)));
        } catch (Exception e) {
            //TODO log in protocol
            e.printStackTrace();
        }
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