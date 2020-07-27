package net.hydrogen2oxygen.hyperselenium.domain;

import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

/**
 * A scenario is an automation of a browser, the execution of one or more scripts, which results in a protocol object.
 */
public class Scenario {

    /**
     * The driver which is used during the entire execution of the scenario
     */
    private HyperWebDriver driver;

    /**
     * The main script
     */
    private Script script;

    /**
     * Protocol with details of the execution
     */
    private Protocol protocol;

    /**
     * The current line of the script not yet executed. Begins with 1.
     */
    private Integer currentLine;

    public HyperWebDriver getDriver() {
        return driver;
    }

    public void setDriver(HyperWebDriver driver) {
        this.driver = driver;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Integer getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(Integer currentLine) {
        this.currentLine = currentLine;
    }
}
