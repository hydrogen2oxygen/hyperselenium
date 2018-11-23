package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
public class ClickCommand extends BaseCommand {

    private String id;
    private String tag;
    private String text;

    @JsonIgnore
    @Override
    public CommandResult executeCommand(HyperWebDriver driver) {

        CommandResult commandResult = new CommandResult();

        try {
            driver.click(id);
        } catch (Exception e) {

        }

        return commandResult;
    }
}