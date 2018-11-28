package net.hydrogen2oxygen.hyperselenium.domain;

import lombok.Data;

@Data
public class CommandResult {

    private Boolean success = false;
    private String message;
}
