package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class DropDownCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult result = new CommandResult();

        driver.selectOption(params[0], params[1]);

        result.setSuccess(true);
        result.setMessage("select option " + params[1] + " from element with id " + params[0] + " successful!");
        return result;
    }

    @Override
    public String getCommandName() {
        return "dropdown";
    }
}
