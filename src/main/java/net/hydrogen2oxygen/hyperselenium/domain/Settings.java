package net.hydrogen2oxygen.hyperselenium.domain;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.util.ArrayList;
import java.util.List;

@Indices({
        @Index(value = "name", type = IndexType.Unique)
})
public class Settings {

    private String name = "hyperSeleniumSettings";

    private List<KeyValue> settings = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyValue> getSettings() {
        return settings;
    }

    public void setSettings(List<KeyValue> settings) {
        this.settings = settings;
    }
}
