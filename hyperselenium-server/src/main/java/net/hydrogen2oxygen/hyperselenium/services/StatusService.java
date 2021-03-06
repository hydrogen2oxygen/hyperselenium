package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StatusService {

    @Value("${build.version}")
    private String buildVersion;

    private ServiceStatus serviceStatus;

    @Autowired
    private SimpMessagingTemplate template;

    @PostConstruct
    public void initService() throws Exception{

        serviceStatus = new ServiceStatus();
        serviceStatus.setBuildVersion(buildVersion);
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void addScenarioUpdate(Scenario scenario) {

        Scenario oldScenario = getServiceStatus().getScenario(scenario.getName());

        if (oldScenario != null) {
            serviceStatus.getScenarios().remove(oldScenario);
        }

        serviceStatus.getScenarios().add(scenario);
        sendStatus();
    }

    public Scenario getRunningScenario(String name) {
        return getServiceStatus().getScenario(name);
    }

    /**
     * Force the send status
     */
    public void sendStatus() {
        sendStatusViaWebsocket();
    }

    private void sendStatusViaWebsocket() {
        template.convertAndSend("/status", serviceStatus);
    }

}
