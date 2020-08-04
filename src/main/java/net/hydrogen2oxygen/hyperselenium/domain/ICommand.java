package net.hydrogen2oxygen.hyperselenium.domain;

import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

public interface ICommand {

    CommandResult executeCommand(HyperWebDriver driver, String [] params) throws CommandExecutionException;
    String getCommandName();
}
