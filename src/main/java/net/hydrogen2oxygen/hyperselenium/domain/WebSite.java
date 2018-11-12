package net.hydrogen2oxygen.hyperselenium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
public class WebSite {

    private Long id;
    private String url;
    private String hyperseleniumOriginUrl;

    @JsonIgnore
    private HyperWebDriver webDriver;
}