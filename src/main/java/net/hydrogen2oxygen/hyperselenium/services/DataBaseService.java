package net.hydrogen2oxygen.hyperselenium.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.hydrogen2oxygen.hyperselenium.domain.KeyValue;
import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.Script;
import net.hydrogen2oxygen.hyperselenium.domain.Settings;
import org.dizitart.no2.*;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.dizitart.no2.IndexOptions.indexOptions;
import static org.dizitart.no2.filters.Filters.eq;

@Service
public class DataBaseService {

    public static final String SCREENSHOTS_PATH = "screenshots-path";
    private Nitrite db;
    private NitriteCollection scenariosCollection;
    private NitriteCollection scriptCollection;
    private ObjectRepository<Settings> settingsObjectRepository;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void initService() throws Exception{

        db = Nitrite.builder()
                .compressed()
                .filePath("hyperselenium.db")
                .openOrCreate("user", "password");

        scenariosCollection = db.getCollection("scenarios");
        scriptCollection = db.getCollection("scripts");
        settingsObjectRepository = db.getRepository(Settings.class);

        createIndexes();

        objectMapper = new ObjectMapper();
    }

    private void createIndexes() {
        if (!scenariosCollection.hasIndex("name")) {
            scenariosCollection.createIndex("name", indexOptions(IndexType.Unique));
        }

        if (!scriptCollection.hasIndex("name")) {
            scriptCollection.createIndex("name", indexOptions(IndexType.Unique));
        }
    }

    public Settings getSettings() {

        org.dizitart.no2.objects.Cursor<Settings> cursor = settingsObjectRepository.find(ObjectFilters.eq("name", "hyperSeleniumSettings"));

        for (Settings s : cursor) {
            return s;
        }

        Settings settings = new Settings();
        settings.getSettings().add(new KeyValue(SCREENSHOTS_PATH,"screenshots/","string"));
        settings.getSettings().add(new KeyValue("screenshots","true","boolean"));
        return settings;
    }

    public String getSetting(String key) {

        Settings settings = getSettings();

        for (KeyValue keyValue : settings.getSettings()) {
            if (keyValue.getKey().equals(key)) return keyValue.getValue();
        }

        return null;
    }

    public void saveSettings(Settings settings) {

        org.dizitart.no2.objects.Cursor<Settings> cursor = settingsObjectRepository.find(ObjectFilters.eq("name", "hyperSeleniumSettings"));

        for (Settings s : cursor) {
            s.setSettings(settings.getSettings());
            settingsObjectRepository.update(s);
            return;
        }

        settingsObjectRepository.insert(settings);
    }

    public Document saveScenario(Scenario scenario) throws JsonProcessingException {

        Document doc = createDocumentFromScenario(scenario);

        WriteResult resultScenario = scenariosCollection.insert(doc);

        if (resultScenario.getAffectedCount() == 1) {
            // ...
        }

        saveScript(scenario.getScript());

        return doc;
    }

    public Document updateScenario(Scenario scenario) throws JsonProcessingException {
        Document doc = createDocumentFromScenario(scenario);

        WriteResult resultScenario = scenariosCollection.update(eq("name", scenario.getName()), doc);

        if (resultScenario.getAffectedCount() == 1) {
            // ...
        }

        updateScript(scenario.getScript());

        return doc;
    }

    private Document updateScript(Script script) throws JsonProcessingException {
        Document scriptDocument = createDocumentFromScript(script);
        scriptCollection.update(eq("name", script.getName()), scriptDocument);
        return scriptDocument;
    }

    public Document saveScript(Script script) throws JsonProcessingException {
        Document scriptDocument = createDocumentFromScript(script);
        scriptCollection.insert(scriptDocument);
        return scriptDocument;
    }

    public List<Scenario> getAllScenarios() throws IOException {

        Cursor cursor = scenariosCollection.find();
        List<Scenario> list = new ArrayList<>();

        for (Document document : cursor) {
            Scenario scenario = createScenarioFromDocument(document);
            list.add(scenario);
        }

        return list;
    }

    public Scenario getScenarioByName(String name) throws IOException {

        Cursor cursor = scenariosCollection.find(eq("name",name));
        return createScenarioFromDocument(cursor.firstOrDefault());
    }

    private Scenario createScenarioFromDocument(Document document) throws IOException {

        if (document == null) return null;

        Scenario scenario = new Scenario();
        scenario.setName((String) document.get("name"));
        scenario.setDescription((String) document.get("description"));
        scenario.setScript(loadScriptByName((String) document.get("script")));
        return scenario;
    }

    private Script loadScriptByName(String scriptName) throws IOException {

        Cursor cursor = scriptCollection.find(eq("name", scriptName));

        for (Document document : cursor) {
            return objectMapper.readValue((String)document.get("json"), Script.class);
        }

        return null;
    }

    private Document createDocumentFromScenario(Scenario scenario) throws JsonProcessingException {

        Script script = scenario.getScript();

        if (script == null) {
            // create new main script
            script = new Script();
            script.setName("mainScript-" + scenario.getName());
            scenario.setScript(script);
        }

        return Document.createDocument("name",scenario.getName())
                    .put("description", scenario.getDescription())
                    .put("script", script.getName());
    }

    private Document createDocumentFromScript(Script script) throws JsonProcessingException {

        Document scriptDoc = Document.createDocument("name", script.getName())
                .put("json", objectMapper.writeValueAsString(script));

        return scriptDoc;
    }
}
