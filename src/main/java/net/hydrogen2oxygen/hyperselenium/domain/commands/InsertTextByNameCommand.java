package net.hydrogen2oxygen.hyperselenium.domain.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class InsertTextByNameCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {

        CommandResult commandResult = new CommandResult();

        String id = params[0];
        String text = params[1];
        driver.insertTextByName(id, text);

        commandResult.setMessage(String.format("Text '%s' inserted into element with id %s", text, id));

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "textByName";
    }
}
