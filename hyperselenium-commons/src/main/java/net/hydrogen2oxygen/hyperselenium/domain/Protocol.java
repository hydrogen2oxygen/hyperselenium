package net.hydrogen2oxygen.hyperselenium.domain;

import java.util.ArrayList;
import java.util.List;

public class Protocol {

    private String scriptName;
    private List<ProtocolLine> lines = new ArrayList<>();
    private String status = "NEW";
    private Integer currentLine;

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public List<ProtocolLine> getLines() {
        return lines;
    }

    public void setLines(List<ProtocolLine> lines) {
        this.lines = lines;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(Integer currentLine) {
        this.currentLine = currentLine;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "scriptName='" + scriptName + '\'' +
                ", lines=" + lines +
                ", status='" + status + '\'' +
                ", currentLine=" + currentLine +
                '}';
    }
}
