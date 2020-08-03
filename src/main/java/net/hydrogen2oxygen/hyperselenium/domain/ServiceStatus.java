package net.hydrogen2oxygen.hyperselenium.domain;

import java.util.HashMap;
import java.util.Map;

public class ServiceStatus {

    private String buildVersion;

    private Map<String,Scenario> scenarioMap = new HashMap<>();

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public Map<String, Scenario> getScenarioMap() {
        return scenarioMap;
    }

    public void setScenarioMap(Map<String, Scenario> scenarioMap) {
        this.scenarioMap = scenarioMap;
    }
}
