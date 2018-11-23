package net.hydrogen2oxygen.hyperselenium.domain.commands;

import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
public class InsertTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver) {
        return null;
    }
}