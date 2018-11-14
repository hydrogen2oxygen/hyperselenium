package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;

@Data
public class ClickCommand extends BaseCommand {

    @JsonIgnore
    @Override
    public CommandResult executeCommand() {
        return null;
    }
}