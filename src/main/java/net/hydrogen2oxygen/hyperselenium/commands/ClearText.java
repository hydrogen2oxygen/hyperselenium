package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class ClearText extends BaseCommand {
    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult result = new CommandResult();

        driver.insertText(params[0],"");

        result.setMessage("Text field with id " + params[0] + "cleared successfully!");
        result.setValue(true);

        return result;
    }

    @Override
    public String getCommandName() {
        return "clearText";
    }
}
