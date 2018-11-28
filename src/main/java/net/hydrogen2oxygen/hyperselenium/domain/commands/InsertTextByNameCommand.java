package net.hydrogen2oxygen.hyperselenium.domain.commands;

import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
@HyperseleniumCommand
public class InsertTextByNameCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {
        driver.insertTextByName(params[0],params[1]);
        return null;
    }

    @Override
    public String getCommandName() {
        return "textByName";
    }
}