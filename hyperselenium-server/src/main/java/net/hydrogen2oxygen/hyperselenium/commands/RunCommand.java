package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class RunCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {

        CommandResult commandResult = new CommandResult();

        commandResult.setSuccess(true);
        commandResult.setMessage("run script " + params[0]);
        commandResult.setSpecialCommand("run");

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "run";
    }
}
