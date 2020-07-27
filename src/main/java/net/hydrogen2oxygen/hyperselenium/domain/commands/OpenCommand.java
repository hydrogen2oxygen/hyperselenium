package net.hydrogen2oxygen.hyperselenium.domain.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@Data
@HyperseleniumCommand
public class OpenCommand extends BaseCommand {

    @JsonIgnore
    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) {

        CommandResult commandResult = new CommandResult();
        String url = params[0];
        try {
            driver.openPage(url).waitMillis(500);
            commandResult.setSuccess(true);
            commandResult.setMessage(String.format("Open website %s successful",params[0]));
        } catch (Exception e) {
            commandResult.setMessage(e.getMessage());
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "open";
    }
}
