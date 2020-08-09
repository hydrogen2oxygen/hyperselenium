package net.hydrogen2oxygen.hyperselenium.commands;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.exceptions.CommandExecutionException;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

import java.io.File;
import java.io.IOException;

@HyperseleniumCommand
public class ScreenshotCommand extends BaseCommand {

    @Override
    public CommandResult executeCommand(HyperWebDriver driver, String[] params) throws CommandExecutionException {

        CommandResult commandResult = new CommandResult();
        commandResult.setSuccess(true);

        try {
            File screenshotFile = driver.screenshot();
            commandResult.setSuccess(true);
            String id = screenshotFile.getAbsolutePath().substring(screenshotFile.getAbsolutePath().lastIndexOf("screenshot") + "screenshot".length()).replace(".png","");
            commandResult.setMessage("screenshot " + id);
        } catch (IOException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        }

        return commandResult;
    }

    @Override
    public String getCommandName() {
        return "screenshot";
    }
}
