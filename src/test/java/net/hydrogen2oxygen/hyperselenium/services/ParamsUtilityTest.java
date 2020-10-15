package net.hydrogen2oxygen.hyperselenium.services;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.Script;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsUtilityTest {

    private ParamsUtility paramsUtility = new ParamsUtility();

    @Test
    public void testSplitParams() {

        checkParams(paramsUtility.splitParams("id"), "id");
        checkParams(paramsUtility.splitParams("a id"), "a", "id");
        checkParams(paramsUtility.splitParams("id \"text bla bla\""), "id", "text bla bla");

        checkParams(paramsUtility.getParamsFromCommandLine("click id \"text bla bla\""), "id", "text bla bla");
    }

    @Test
    public void testReplaceVariablesInParameters() {

        Map<String, String> variables = new HashMap<>();
        variables.put("#user", "John");

        String[] params = {"run", "login"};
        params = paramsUtility.replaceVariablesInParameters(params, variables);
        Assert.assertEquals(params[0], "run");
        Assert.assertEquals(params[1], "login");

        String[] params2 = {"text", "#user"};
        params2 = paramsUtility.replaceVariablesInParameters(params2, variables);
        Assert.assertEquals(params2[0], "text");
        Assert.assertEquals(params2[1], "John");
    }

    @Test
    public void testMissingParameters() {

        Map<String, String> variables = new HashMap<>();

        Scenario scenario = new Scenario();

        Script script = new Script();
        List<String> lines = new ArrayList<>();
        lines.add("# THIS IS A TEST");
        lines.add(" a stupid line of #comment");
        lines.add("I set a variable, but this should not be included in the missing ones!");
        lines.add("    #userName = John");
        lines.add("Instead the variable for the well known phrase is NOT missing");
        lines.add("    #aWellKnownPhrase = Make love not war");
        lines.add("But this variable should not be considered missing ...");
        lines.add("    open #unknownUrl");
        lines.add("    click #unknownIdForButton");
        lines.add("    text inputId #aWellKnownPhrase");
        lines.add("    screenshot");
        lines.add("    close");
        script.setLines(lines);
        scenario.setScript(script);

        scenario.getVariables().putAll(paramsUtility.extractVariables(scenario));
        scenario.setMissingVariables(paramsUtility.extractMissingVariables(scenario));

        Assert.assertNotNull(scenario.getMissingVariables());
        Assert.assertTrue("Expected size is not 2 but " + scenario.getMissingVariables().size(), scenario.getMissingVariables().size() == 2);
    }

    private void checkParams(String[] parts, String... expectedParts) {

        Assert.assertEquals(parts.length, expectedParts.length);

        for (int i = 0; i < expectedParts.length; i++) {
            Assert.assertEquals(expectedParts[i], parts[i]);
        }
    }

}
