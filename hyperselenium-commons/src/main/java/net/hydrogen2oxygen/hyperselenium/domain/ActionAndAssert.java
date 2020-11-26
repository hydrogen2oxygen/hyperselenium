package net.hydrogen2oxygen.hyperselenium.domain;

public class ActionAndAssert {

    private ICommand command;
    private IAssert anAssert;

    public ICommand getCommand() {
        return command;
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public IAssert getAnAssert() {
        return anAssert;
    }

    public void setAnAssert(IAssert anAssert) {
        this.anAssert = anAssert;
    }
}
