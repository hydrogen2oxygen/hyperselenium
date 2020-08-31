package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@HyperseleniumCommand
public class WaitForJavaScriptCommand extends BaseCommand {


    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult result = new CommandResult();

        driver.waitForJavascript(params[0]);

        result.setSuccess(true);
        result.setMessage("Waiting for script [" + params[0] + "] successful!");
        return result;
    }

    @Override
    public String getCommandName() {
        return "waitForJavaScript";
    }
}
