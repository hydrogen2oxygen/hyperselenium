package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.domain.ActionAndAssert;
import net.hydrogen2oxygen.hyperselenium.domain.ICommand;
import net.hydrogen2oxygen.hyperselenium.domain.commands.ClickCommand;
import net.hydrogen2oxygen.hyperselenium.domain.commands.InsertTextCommand;
import org.junit.Test;

import java.io.IOException;

public class DataServiceTest {

    @Test
    public void testPersistence() throws IOException {

        ClickCommand click = new ClickCommand();
        ICommand insertText = new InsertTextCommand();

        ActionAndAssert actionAndAssert1 = new ActionAndAssert();
        actionAndAssert1.setCommand(click);

        ActionAndAssert actionAndAssert2 = new ActionAndAssert();
        actionAndAssert2.setCommand(insertText);

    }
}
