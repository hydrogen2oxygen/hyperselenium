package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.ServiceStatus;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HyperseleniumRestAdapter {

    private static final Logger logger = LogManager.getLogger(HyperseleniumRestAdapter.class);

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("serviceStatus")
    ResponseEntity<ServiceStatus> getServiceStatus() {

        final EntryMessage entryMessage = logger.traceEntry("give me the service status");


        ServiceStatus serviceStatus = hyperseleniumService.getServiceStatus();
        template.convertAndSend("/status", serviceStatus);

        return logger.traceExit(entryMessage, ResponseEntity.ok(serviceStatus));
    }

    // TODO Find scenario by name

    // TODO Update scenarios

    // TODO Delete scenarios

    // TODO CRUD Scripts

    @PostMapping("closeAllDrivers")
    String closeAllDrivers() {

        hyperseleniumService.closeAllDrivers();
        return "OK";
    }
}

