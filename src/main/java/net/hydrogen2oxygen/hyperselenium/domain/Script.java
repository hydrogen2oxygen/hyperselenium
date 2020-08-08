package net.hydrogen2oxygen.hyperselenium.domain;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.util.ArrayList;
import java.util.List;

@Indices({
        @Index(value = "name", type = IndexType.Unique)
})
public class Script {

    @Id
    private String name;

    /**
     * The script line by line
     */
    private List<String> lines;

    /**
     * A list of breakpoints
     */
    private List<Integer> breakpoints = new ArrayList<>();

    public Script(){}

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

    public List<Integer> getBreakpoints() {
        return breakpoints;
    }

    public void setBreakpoints(List<Integer> breakpoints) {
        this.breakpoints = breakpoints;
    }

    @Override
    public String toString() {
        return "Script{" +
                "name='" + name + '\'' +
                ", lines=" + lines +
                ", breakpoints=" + breakpoints +
                '}';
    }
}
