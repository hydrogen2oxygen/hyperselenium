package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HyperseleniumRestAdapter {

    @Autowired
    private HyperseleniumService hyperseleniumService;


    @PostMapping("/closeAllDrivers")
    String closeAllDrivers() {

        hyperseleniumService.closeAllDrivers();
        return "OK";
    }
}

