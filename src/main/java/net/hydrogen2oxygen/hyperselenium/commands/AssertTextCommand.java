package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class AssertTextCommand extends BaseCommand{

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();

        if (driver.getHtml().contains(params[0])) {
            commandResult.setSuccess(true);
        } else {
            throw new CommandExecutionException(String.format("ASSERT ERROR: text '%s' not found on page!", params[0]));
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "assertText";
    }
}
