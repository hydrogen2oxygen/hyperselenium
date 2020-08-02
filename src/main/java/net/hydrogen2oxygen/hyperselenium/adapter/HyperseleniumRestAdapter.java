package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.ServiceStatus;
import net.hydrogen2oxygen.hyperselenium.services.DataBaseService;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HyperseleniumRestAdapter {

    private static final Logger logger = LogManager.getLogger(HyperseleniumRestAdapter.class);

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @Autowired
    private SimpMessagingTemplate template;

    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private DataBaseService dataBaseService;

    @GetMapping("serviceStatus")
    ResponseEntity<ServiceStatus> getServiceStatus() {

        final EntryMessage entryMessage = logger.traceEntry("give me the service status");


        ServiceStatus serviceStatus = hyperseleniumService.getServiceStatus();
        template.convertAndSend("/status", serviceStatus);

        return logger.traceExit(entryMessage, ResponseEntity.ok(serviceStatus));
    }

    @PostMapping("play/{name}")
    ResponseEntity<Scenario> play(@PathVariable String name) throws IOException {

        final Scenario scenario = dataBaseService.getScenarioByName(name);

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                hyperseleniumService.executeScenario(scenario);
            }
        });

        return ResponseEntity.ok(scenario);
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

