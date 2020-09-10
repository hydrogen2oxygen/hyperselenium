package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.*;
import net.hydrogen2oxygen.hyperselenium.services.DataBaseService;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import net.hydrogen2oxygen.hyperselenium.services.StatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HyperseleniumRestAdapter {

    private static final Logger logger = LogManager.getLogger(HyperseleniumRestAdapter.class);

    @Autowired
    private DataBaseService dataBaseService;

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @Autowired
    private StatusService statusService;

    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    @GetMapping("settings")
    ResponseEntity<Settings> getSettings() {

        return ResponseEntity.ok(dataBaseService.getSettings());
    }

    @PutMapping("settings")
    ResponseEntity<Settings> setSettings(@RequestBody Settings settings) {

        dataBaseService.saveSettings(settings);
        return ResponseEntity.ok(dataBaseService.getSettings());
    }

    @GetMapping("serviceStatus")
    ResponseEntity<ServiceStatus> getServiceStatus() {

        final EntryMessage entryMessage = logger.traceEntry("give me the service status");

        ServiceStatus serviceStatus = statusService.getServiceStatus();

        return logger.traceExit(entryMessage, ResponseEntity.ok(serviceStatus));
    }

    @GetMapping("commands")
    ResponseEntity<List<Command>> getCommands() {
        return ResponseEntity.ok(hyperseleniumService.getCommands());
    }

    @PostMapping("play/{name}")
    ResponseEntity<Scenario> play(@PathVariable String name) throws IOException {

        if (statusService.getRunningScenario(name) != null) {
            return continueAtLineNumber(name, 0);
        }

        Scenario scenario = dataBaseService.getScenarioByName(name);

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                hyperseleniumService.executeScenario(scenario);
            }
        });

        return ResponseEntity.ok(scenario);
    }

    @PostMapping("play/{name}/{lineNumber}")
    ResponseEntity<Scenario> continueAtLineNumber(@PathVariable String name, @PathVariable Integer lineNumber) throws IOException {

        final Scenario scenario = statusService.getRunningScenario(name);

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                hyperseleniumService.executeScenario(scenario, lineNumber);
            }
        });

        return ResponseEntity.ok(scenario);
    }

    @PostMapping("export/{name}")
    ResponseEntity<Scenario> exportScript(@PathVariable String name) throws IOException {

        final Scenario scenario = dataBaseService.exportScript(name);

        return ResponseEntity.ok(scenario);
    }

    @PutMapping("stop/{name}")
    ResponseEntity<Scenario> stop(@PathVariable String name) throws IOException {

        hyperseleniumService.stopScenario(name);
        final Scenario scenario = dataBaseService.getScenarioByName(name);
        return ResponseEntity.ok(scenario);
    }

    @PutMapping("close/{name}")
    ResponseEntity<Scenario> close(@PathVariable String name) throws IOException {

        final Scenario scenario = dataBaseService.getScenarioByName(name);
        hyperseleniumService.closeScenario(scenario);
        return ResponseEntity.ok(scenario);
    }

    @PutMapping("closeAll")
    ResponseEntity<CommandResult> closeAll() throws IOException {

        hyperseleniumService.closeAllDrivers();
        CommandResult commandResult = new CommandResult();
        commandResult.setSuccess(true);
        return ResponseEntity.ok(commandResult);
    }

    @PostMapping("closeAllDrivers")
    String closeAllDrivers() {

        hyperseleniumService.closeAllDrivers();
        return "OK";
    }

    @Bean(name = "taskExecutor")
    @Primary
    public TaskExecutor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        return executor;
    }
}

