package net.hydrogen2oxygen.hyperselenium.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.services.DataBaseService;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import net.hydrogen2oxygen.hyperselenium.services.StatusService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/scenario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScenarioRestAdapter {

    private static final Logger logger = LogManager.getLogger(ScenarioRestAdapter.class);

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @Autowired
    private StatusService statusService;

    /**
     * Create scenario
     * @param scenario
     * @return
     */
    @PostMapping
    Scenario createScenario(@RequestBody Scenario scenario) throws JsonProcessingException {

        dataBaseService.saveScenario(scenario);
        return scenario;
    }

    /**
     * Read all scenarios
     * @return
     * @throws IOException
     */
    @GetMapping
    ResponseEntity<List<Scenario>> getAllScenarios() throws IOException {

        return ResponseEntity.ok(dataBaseService.getAllScenarios());
    }

    @GetMapping("{name}")
    ResponseEntity<Scenario> getScenarioByName(@PathVariable String name) throws IOException {

        Scenario scenario = dataBaseService.getScenarioByName(name);

        if (scenario != null) {
            scenario.setProtocol(hyperseleniumService.addNewProtocol(scenario.getScript()));
            return ResponseEntity.ok(scenario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/screenshot/{id}")
    @ResponseBody
    public HttpEntity<byte[]> getArticleImage(@PathVariable String id) throws IOException {

        String filePath = String.format("%s/screenshot%s.png", dataBaseService.getSetting(DataBaseService.SCREENSHOTS_PATH), id);
        byte[] imageData = FileUtils.readFileToByteArray(new File( filePath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageData.length);

        return new HttpEntity<byte[]>(imageData, headers);
    }

    @PutMapping
    Scenario updateScenario(@RequestBody Scenario scenario) throws JsonProcessingException {

        dataBaseService.updateScenario(scenario);
        statusService.addScenarioUpdate(scenario);
        statusService.sendStatus();
        return scenario;
    }

    @DeleteMapping("{name}")
    ResponseEntity<Scenario> deleteScenario(@PathVariable String name) throws IOException {

        final Scenario scenario = dataBaseService.getScenarioByName(name);
        hyperseleniumService.closeScenario(scenario);
        dataBaseService.deleteScenario(name);
        return ResponseEntity.ok(scenario);
    }

}

