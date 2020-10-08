package net.hydrogen2oxygen.hyperselenium.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamsUtility {

    public String[] getParamsFromCommandLine(String line) {
        String [] parts = line.trim().split(" ");
        return splitParams(line.replace(parts[0] + " ",""));
    }

    public String[] splitParams(String params) {

        String parts [] = params.trim().split(" ");

        // if it contains "longer text in quotation marks" than this is one single string
        if (params.contains("\"")) {

            List<String> assembleList = new ArrayList<>();

            String text = "";
            boolean textBegins = false; // switch on and off depending on content

            for (String part : parts) {

                if (part.contains("\"") && textBegins) {
                    if (text.length() > 0) text += " ";
                    text += part;
                    text = text.replaceAll("\"","");
                    textBegins = false;
                    assembleList.add(text);
                    text = "";
                } else if (part.contains("\"") && !textBegins) {
                    text = part;
                    textBegins = true;
                } else if (textBegins) {
                    if (text.length() > 0) text += " ";
                    text += part;
                } else {
                    assembleList.add(part);
                }
            }

            parts  = new String[assembleList.size()];
            return assembleList.toArray(parts);
        }

        return parts;
    }

    public String[] replaceVariablesInParameters(String parameters[], Map<String, String> variables) {

        for (int i=0; i<parameters.length; i++) {

            String value = parameters[i];

            if (variables.get(value) != null) {
                parameters[i] = variables.get(value);
            }
        }

        return parameters;
    }
}
