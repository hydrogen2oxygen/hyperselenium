package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.ICommand;
import org.apache.commons.lang.NotImplementedException;

import java.util.Map;

public abstract class BaseCommand implements ICommand {

    @Override
    public String getSyntax() {
        return "";
    }

    @Override
    public Map<String,String> getParameters(String[] params) {
        throw new NotImplementedException("This HyperSelenium Command has not implemented the very important getParameters() function.");
    }
}
