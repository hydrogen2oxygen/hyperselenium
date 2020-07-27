package net.hydrogen2oxygen.hyperselenium.domain.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class InsertTextByNameCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {
        String id = params[0];
        String text = params[1];
        driver.insertTextByName(id, text);
        return null;
    }

    @Override
    public String getCommandName() {
        return "textByName";
    }
}
