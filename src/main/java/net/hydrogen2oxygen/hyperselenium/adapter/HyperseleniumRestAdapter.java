package net.hydrogen2oxygen.hyperselenium.adapter;

import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HyperseleniumRestAdapter {

    @Autowired
    private HyperseleniumService hyperseleniumService;

    @PostMapping("/openWebsite")
    WebSite openWebsite(@RequestBody WebSite webSite) throws IOException {

        return hyperseleniumService.openWebsite(webSite);
    }

    @GetMapping("/closeWebsite/{uuid}")
    WebSite closeWebsite(@PathVariable String uuid) {

        return hyperseleniumService.closeWebsite(uuid);
    }
}
