package net.hydrogen2oxygen.hyperselenium.domain;

import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

public interface ICommand {

    CommandResult executeCommand(HyperWebDriver driver, String [] params);
    String getCommandName();
}
