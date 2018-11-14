package net.hydrogen2oxygen.hyperselenium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

import java.util.ArrayList;
import java.util.List;

@Data
public class WebSite {

    private Long id;
    private String url;
    private String hyperseleniumOriginUrl;

    private List<ActionAndAssert> actionAndAssertList = new ArrayList<>();

    @JsonIgnore
    private HyperWebDriver webDriver;
}