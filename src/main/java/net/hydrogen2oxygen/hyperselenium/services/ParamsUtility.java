package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;

import java.util.*;

public class ParamsUtility {

    public String[] getParamsFromCommandLine(String line) {
        String[] parts = line.trim().split(" ");
        return splitParams(line.replace(parts[0] + " ", ""));
    }

    public String[] splitParams(String params) {

        String parts[] = params.trim().split(" ");

        // if it contains "longer text in quotation marks" than this is one single string
        if (params.contains("\"")) {

            List<String> assembleList = new ArrayList<>();

            String text = "";
            boolean textBegins = false; // switch on and off depending on content

            for (String part : parts) {

                if (part.contains("\"") && textBegins) {
                    if (text.length() > 0) text += " ";
                    text += part;
                    text = text.replaceAll("\"", "");
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

            parts = new String[assembleList.size()];
            return assembleList.toArray(parts);
        }

        return parts;
    }

    public String[] replaceVariablesInParameters(String parameters[], Map<String, String> variables) {

        for (int i = 0; i < parameters.length; i++) {

            String value = parameters[i];

            if (variables.get(value) != null) {
                parameters[i] = variables.get(value);
            }
        }

        return parameters;
    }

    /**
     * Missing variables extraction, making sure that the user gets a chance to see what is missing at least
     *
     * @param scenario
     * @return set of missing variables
     */
    public Set<String> extractMissingVariables(Scenario scenario) {

        Set<String> missingVariables = new TreeSet<>();

        if (scenario == null) return missingVariables;
        if (scenario.getScript() == null) return missingVariables;
        if (scenario.getScript().getLines() == null) return missingVariables;

        for (String line : scenario.getScript().getLines()) {

            if (!line.startsWith("    ")) continue;

            String params[] = getParamsFromCommandLine(line);
            int i = 0;

            for (String param : params) {

                i++;

                if (i >= 1 && param.contains("#") && !scenario.getVariables().containsKey(param)) {
                    missingVariables.add(param);
                }
            }
        }

        return missingVariables;
    }

    public Map<String, String> extractVariables(Scenario scenario) {

        Map<String, String> variables = new HashMap<>();

        for (String line : scenario.getScript().getLines()) {
            if (line.startsWith("    #") && line.contains("=")) {
                String keyValue[] = extractKeyValue(line);
                variables.put(keyValue[0], keyValue[1]);
            }
        }

        return variables;
    }

    public String[] extractKeyValue(String line) {
        String variable[] = line.trim().split("=");
        String key = variable[0].trim();
        String value = variable[1].trim().replaceAll("\\\"", "");
        return new String[]{key,value};
    }
}
