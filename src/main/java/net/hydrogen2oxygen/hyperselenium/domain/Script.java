package net.hydrogen2oxygen.hyperselenium.domain;

import java.util.ArrayList;
import java.util.List;

public class Script {

    private String name;

    /**
     * The script line by line
     */
    private List<String> lines;

    /**
     * The current line of the script not yet executed. Begins with 1.
     */
    private Integer currentLine = 1;

    /**
     * A list of breakpoints
     */
    private List<Integer> breakpoint = new ArrayList<>();

    public Script(String name, List<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public Integer getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(Integer currentLine) {
        this.currentLine = currentLine;
    }

    public List<Integer> getBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(List<Integer> breakpoint) {
        this.breakpoint = breakpoint;
    }
}
