package net.hydrogen2oxygen.hyperselenium.adapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.services.DataBaseService;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/scenario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScenarioRestAdapter {

    private static final Logger logger = LogManager.getLogger(ScenarioRestAdapter.class);


    @Autowired
    private HyperseleniumService hyperseleniumService;

    private DataBaseService dataBaseService;

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
            hyperseleniumService.addNewProtocol(scenario);
            return ResponseEntity.ok(scenario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    Scenario updateScenario(@RequestBody Scenario scenario) throws JsonProcessingException {

        dataBaseService.updateScenario(scenario);
        return scenario;
    }

    // TODO Delete scenarios

    // TODO CRUD Scripts

    @Autowired
    public void setDataBaseService(DataBaseService dataBaseService){
        this.dataBaseService = dataBaseService;
    }
}

