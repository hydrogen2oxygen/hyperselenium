package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.ServiceStatus;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*")
public class HyperseleniumRestAdapter {

    private static final Logger logger = LogManager.getLogger(HyperseleniumRestAdapter.class);

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @Value("${build.version}")
    private String buildVersion;

    @GetMapping("serviceStatus")
    ResponseEntity<ServiceStatus> getBuildVersion() {

        final EntryMessage entryMessage = logger.traceEntry("give me the service status");

        ServiceStatus status = new ServiceStatus();
        status.setBuildVersion(buildVersion);
        return logger.traceExit(entryMessage, ResponseEntity.ok(status));
    }

    @PostMapping("closeAllDrivers")
    String closeAllDrivers() {

        hyperseleniumService.closeAllDrivers();
        return "OK";
    }
}

