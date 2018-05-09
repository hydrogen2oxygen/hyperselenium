package net.hydrogen2oxygen.configuration;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Logger
public class RunArguments {

    @Getter
    private Map<String,String> keyValues = new HashMap<>();

    public RunArguments(String [] args) {

        for (String argument : args) {
            if (argument.contains("=") && !argument.startsWith("-D")) {
                String parts [] = argument.split("=");
                keyValues.put(parts[0],parts[1]);
            }
        }

    }
}
