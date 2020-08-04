package net.hydrogen2oxygen.hyperselenium.domain;

import java.util.ArrayList;
import java.util.List;

public class ServiceStatus {

    private String buildVersion;

    private List<Scenario> scenarios = new ArrayList<>();

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
}
