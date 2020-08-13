package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class InsertTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();

        if (params.length < 2) {
            throw new CommandExecutionException("ERROR for 'text', not enough params!");
        }

        driver.insertText(params[0],params[1]);

        commandResult.setSuccess(true);

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "text";
    }
}
