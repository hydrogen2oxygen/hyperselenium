package net.hydrogen2oxygen.hyperselenium.domain.commands;

import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;

@Data
public class InsertTextCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand() {
        return null;
    }
}