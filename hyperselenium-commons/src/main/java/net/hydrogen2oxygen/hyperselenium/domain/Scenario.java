package net.hydrogen2oxygen.hyperselenium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A scenario is an automation of a browser, the execution of one or more scripts, which results in a protocol object.
 */
@Indices({
        @Index(value = "name", type = IndexType.Unique)
})
public class Scenario {

    /**
     * The driver which is used during the entire execution of the scenario
     */
    @JsonIgnore
    private HyperWebDriver driver;

    /**
     * Variables during a run
     */
    @JsonIgnore
    private Map<String, String> variables = new HashMap<>();

    @JsonIgnore
    private Set<String> missingVariables = new TreeSet<>();

    /**
     * Unique name
     */
    @Id
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

    public Scenario() {
    }

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

    public Map<String, String> getVariables() {
        return variables;
    }

    public Set<String> getMissingVariables() {
        return missingVariables;
    }

    public void setMissingVariables(Set<String> missingVariables) {
        this.missingVariables = missingVariables;
    }

    @Override
    public String toString() {
        String txt = "Scenario{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", script=" + script +
                ", protocol=" + protocol +
                "}";
        return txt.replaceAll("\\{", "\n\t{")
                .replaceAll("\\}", "\n\t}").trim();
    }

}
