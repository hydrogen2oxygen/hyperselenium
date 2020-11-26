package net.hydrogen2oxygen.hyperselenium.exceptions;

public class CommandExecutionException extends Exception {

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
