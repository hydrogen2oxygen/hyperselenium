package net.hydrogen2oxygen.hyperselenium.domain;

import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

import java.util.Map;

public interface ICommand {

    CommandResult executeCommand(HyperWebDriver driver, String [] params) throws CommandExecutionException;
    String getCommandName();
    String getSyntax();
    Map<String,String> getParameters(String[] params);
}
