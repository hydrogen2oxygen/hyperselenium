package net.hydrogen2oxygen.hyperselenium.domain.commands;

import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.ICommand;

@Data
public abstract class BaseCommand implements ICommand {

    private String id;
    private String tag;
    private String value;
}
