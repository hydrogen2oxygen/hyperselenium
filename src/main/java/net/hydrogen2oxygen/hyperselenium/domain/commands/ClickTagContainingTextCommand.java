package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class ClickTagContainingTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String [] params) {

        CommandResult commandResult = new CommandResult();

        try {
            driver.clickTagContainingText(params[0], params[1]);
            commandResult.setSuccess(true);
            commandResult.setMessage(String.format("Click on tag %s with text %s successful",params[0],params[1]));
        } catch (Exception e) {
            commandResult.setMessage(e.getMessage());
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "clickTagContainingText";
    }
}
