package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class WaitCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();

        try {
            driver.waitMillis(Integer.parseInt(params[0]));
        } catch (InterruptedException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        }
        commandResult.setSuccess(true);
        commandResult.setMessage(String.format("Click on id %s successful", params[0]));

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "wait";
    }
}
