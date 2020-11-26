package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class ClickTagContainingTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();

        driver.clickTagContainingText(params[0], params[1]);
        commandResult.setSuccess(true);
        commandResult.setMessage(String.format("Click on tag %s with text %s successful", params[0], params[1]));

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "clickTagContainingText";
    }
}
