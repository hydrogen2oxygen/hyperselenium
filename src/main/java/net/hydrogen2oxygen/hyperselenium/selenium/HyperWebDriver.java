package net.hydrogen2oxygen.hyperselenium.selenium;

import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.exceptions.HyperWebDriverException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class HyperWebDriver {

    private String screenshotsPath;
    private Boolean closed = false;
    private WebDriver driver;

    public enum DriverTypes {
        LOCAL_CHROME, REMOTE_FIREFOX
    }

    public HyperWebDriver(String driverType) throws HyperWebDriverException {
        init(driverType, null, null);
    }

    public HyperWebDriver(String driverType, String remoteUrl, String seleniumDriverDirectory) throws HyperWebDriverException {

        init(driverType, remoteUrl, seleniumDriverDirectory);
    }

    private void init(String driverType, String remoteUrl, String seleniumDriverDirectory) throws HyperWebDriverException {

        if (DriverTypes.LOCAL_CHROME.name().equals(driverType)) {

            System.setProperty("webdriver.chrome.driver", seleniumDriverDirectory + "/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(false);
            driver = new ChromeDriver(chromeOptions);

        } else if (DriverTypes.REMOTE_FIREFOX.name().equals(driverType)) {

            try {
                FirefoxOptions options = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new HyperWebDriverException("Remote connection could not be established");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new HyperWebDriverException("Unknown option for browser or remote connection");
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public void setScreenshotsPath(String screenshotsPath) {
        this.screenshotsPath = screenshotsPath;
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

    public String getAttribute(String id, String attributeKey) {
        WebElement element = driver.findElement(By.id(id));

        if (element == null) {
            return "";
        }

        return element.getAttribute(attributeKey);
    }

    public HyperWebDriver click(String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }

    public HyperWebDriver clickName(String name) {
        driver.findElement(By.name(name)).click();
        return this;
    }

    public HyperWebDriver clickTagContainingText(String tag, String text) throws CommandExecutionException {

        List<WebElement> list = driver.findElements(By.tagName(tag));

        boolean clickPerformed = false;

        for (WebElement element : list) {

            String elementText = element.getText().trim();

            if (StringUtils.isEmpty(elementText)) {
                continue;
            }

            if (elementText.contains(text)) {
                element.click();
                clickPerformed = true;
                break;
            }
        }

        if (!clickPerformed) {
            throw new CommandExecutionException(String.format("clickTagContainingText with tag = [%s] and text = [%s] was not successful!", tag, text));
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
        if (driver != null) {
            driver.quit();
        }
        closed = true;
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

    public File screenshot() throws IOException {

        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        if (screenshotsPath != null) {
            File folder = new File(screenshotsPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File newFile = new File(screenshotsPath + file.getName());
            FileCopyUtils.copy(file, newFile);
            return newFile;
        }

        return file;
    }

    public String getHtml() {

        return driver.getPageSource();
    }

    public void clear(String id) {
        driver.findElement(By.id(id)).clear();
    }

    public void waitForJQuery() {

        waitForJavascript((JavascriptExecutor) driver,"return jQuery.active");
    }

    public void waitForJavascript(final String script) {
        waitForJavascript((JavascriptExecutor) driver, script);
    }

    private void waitForJavascript(final JavascriptExecutor executor, final String script) {
        new FluentWait<JavascriptExecutor>(executor) {
            protected RuntimeException timeoutException(
                    String message, Throwable lastException) {
                return new RuntimeException(message);
            }
        }.withTimeout(10, TimeUnit.SECONDS)
                .until(new Function<JavascriptExecutor, Boolean>() {
                    public Boolean apply(JavascriptExecutor e) {

                        Object result = executor.executeScript(script);

                        if (result instanceof Long) {
                            return (Long)result == 0;
                        }

                        if (result instanceof Boolean) {
                            return (Boolean)result;
                        }

                        return result != null;
                    }
                });
    }
}
