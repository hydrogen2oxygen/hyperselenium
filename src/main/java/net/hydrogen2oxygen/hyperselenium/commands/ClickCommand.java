package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class ClickCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {

        CommandResult commandResult = new CommandResult();

        driver.click(params[0]);
        commandResult.setSuccess(true);
        commandResult.setMessage(String.format("Click on id %s successful", params[0]));

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "click";
    }
}
