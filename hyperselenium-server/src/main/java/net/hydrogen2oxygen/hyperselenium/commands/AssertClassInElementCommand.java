package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class AssertClassInElementCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();

        try {
            String id = params[0];
            String expectedClass = params[1];
            String classValue = driver.getAttribute(id, "class");

            if (classValue.toLowerCase().contains(expectedClass)) {
                commandResult.setSuccess(true);
            } else {
                commandResult.setSuccess(false);
            }
        } catch (Exception e) {
            throw new CommandExecutionException(String.format("ASSERT ERROR: ", e.getMessage()));
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "assertClassInElement";
    }
}
