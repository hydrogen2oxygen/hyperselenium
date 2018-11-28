package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import org.openqa.selenium.Keys;

@Data
@HyperseleniumCommand
public class EnterCommand extends BaseCommand {

    @JsonIgnore
    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String [] params) {

        CommandResult commandResult = new CommandResult();

        try {
            // FIXME WebSite weiterreichen und auch das zuletzt angeklickte Element
            // driver.getDriver().sendKeys(Keys.RETURN);
            commandResult.setSuccess(true);
            commandResult.setMessage(String.format("Click on id %s successful",params[0]));
        } catch (Exception e) {
            commandResult.setMessage(e.getMessage());
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "enter";
    }
}