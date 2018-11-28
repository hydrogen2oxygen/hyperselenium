package net.hydrogen2oxygen.hyperselenium.domain.commands;

import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
@HyperseleniumCommand
public class InsertTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {
        driver.insertText(params[0],params[1]);
        return null;
    }

    @Override
    public String getCommandName() {
        return "text";
    }
}