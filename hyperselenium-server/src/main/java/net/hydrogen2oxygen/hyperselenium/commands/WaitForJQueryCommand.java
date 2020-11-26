package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class WaitForJQueryCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult result = new CommandResult();

        driver.waitForJQuery();

        result.setSuccess(true);
        result.setMessage("Waiting for JQuery initialization successful!");
        return result;
    }

    @Override
    public String getCommandName() {
        return "waitForJQuery";
    }
}
