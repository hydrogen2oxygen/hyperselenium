package net.hydrogen2oxygen.hyperselenium.domain;

import lombok.Data;

@Data
public class ActionAndAssert {

    private ICommand command;
    private IAssert anAssert;
}