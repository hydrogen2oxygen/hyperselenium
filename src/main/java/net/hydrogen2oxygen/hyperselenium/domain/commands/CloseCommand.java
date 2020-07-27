package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class CloseCommand extends BaseCommand {

    @JsonIgnore
    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {

        CommandResult commandResult = new CommandResult();

        try {
            driver.close();
            commandResult.setSuccess(true);
            commandResult.setMessage("Selenium driver closed!");
        } catch (Exception e) {
            commandResult.setMessage(e.getMessage());
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "close";
    }
}
