package net.hydrogen2oxygen.hyperselenium.domain;

public class ProtocolLine {

    private Integer lineNumber;
    private String type;
    private String status;
    private String line;
    private String result;

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ProtocolLine{" +
                "lineNumber=" + lineNumber +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", line='" + line + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
