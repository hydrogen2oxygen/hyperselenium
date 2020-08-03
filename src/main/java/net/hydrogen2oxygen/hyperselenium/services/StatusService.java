package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Service
public class StatusService {

    @Value("${status.service.min.update.millis:500}")
    private Long minUpdateMillis;

    @Value("${build.version}")
    private String buildVersion;

    private Long lastUpdate;
    private ServiceStatus serviceStatus;

    @Autowired
    private SimpMessagingTemplate template;

    @PostConstruct
    public void initService() throws Exception{

        serviceStatus = new ServiceStatus();
        lastUpdate = Calendar.getInstance().getTimeInMillis();
        serviceStatus.setBuildVersion(buildVersion);

        if (minUpdateMillis == null) minUpdateMillis = 500L;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void addScenarioUpdate(Scenario scenario) {

        serviceStatus.getScenarioMap().put(scenario.getName(), scenario);
        checkUpdate();
    }

    /**
     * Force the send status
     */
    public void sendStatus() {
        sendStatusViaWebsocket();
    }

    private void checkUpdate() {

        if (Calendar.getInstance().getTimeInMillis() > lastUpdate + minUpdateMillis) {
            sendStatusViaWebsocket();
        }
    }

    private void sendStatusViaWebsocket() {
        template.convertAndSend("/status", serviceStatus);
    }

}
