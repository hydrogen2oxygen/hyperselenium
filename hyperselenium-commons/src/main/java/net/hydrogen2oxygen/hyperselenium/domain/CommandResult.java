package net.hydrogen2oxygen.hyperselenium.domain;

public class CommandResult {

    private Boolean success = false;
    private String message;
    private String specialCommand;
    private Object value;

    public String getSpecialCommand() {
        return specialCommand;
    }

    public void setSpecialCommand(String specialCommand) {
        this.specialCommand = specialCommand;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
