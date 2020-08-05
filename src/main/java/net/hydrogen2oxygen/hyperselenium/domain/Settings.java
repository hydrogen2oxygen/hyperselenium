package net.hydrogen2oxygen.hyperselenium.domain;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.util.HashMap;
import java.util.Map;

@Indices({
        @Index(value = "name", type = IndexType.Unique)
})
public class Settings {

    private String name = "hyperSeleniumSettings";

    private Map<String, String> settings = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }
}
