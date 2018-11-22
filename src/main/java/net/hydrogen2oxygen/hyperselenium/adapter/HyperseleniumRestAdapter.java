package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HyperseleniumRestAdapter {

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @PostMapping("/openWebsite")
    WebSite openWebsite(@RequestBody WebSite webSite) throws Exception {

        return hyperseleniumService.openWebsite(webSite);
    }

    @CrossOrigin("*")
    @PostMapping("/closeWebsite")
    WebSite closeWebsite(@RequestBody WebSite webSite) {

        return hyperseleniumService.closeWebsite(webSite.getUuid());
    }

    @PostMapping("/closeAllDrivers")
    String closeAllDrivers() {

        hyperseleniumService.closeAllDrivers();
        return "OK";
    }
}

