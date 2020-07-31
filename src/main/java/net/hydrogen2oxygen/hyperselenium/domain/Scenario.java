package net.hydrogen2oxygen.hyperselenium.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

/**
 * A scenario is an automation of a browser, the execution of one or more scripts, which results in a protocol object.
 */
@JsonRootName(value = "scenario")
public class Scenario {

    /**
     * The driver which is used during the entire execution of the scenario
     */
    private HyperWebDriver driver;

    /**
     * Unique name
     */
    private String name;

    /**
     * A few words describing the scenario
     */
    private String description;

    /**
     * The main script
     */
    private Script script;

    /**
     * Protocol with details of the execution
     */
    private Protocol protocol;

    public HyperWebDriver getDriver() {
        return driver;
    }

    public void setDriver(HyperWebDriver driver) {
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
