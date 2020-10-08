package net.hydrogen2oxygen.hyperselenium.services;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParamsUtilityTest {

    @Test
    public void testSplitParams() {

        ParamsUtility paramsUtility = new ParamsUtility();

        checkParams(paramsUtility.splitParams("id"), "id");
        checkParams(paramsUtility.splitParams("a id"), "a", "id");
        checkParams(paramsUtility.splitParams("id \"text bla bla\""), "id", "text bla bla");

        checkParams(paramsUtility.getParamsFromCommandLine("click id \"text bla bla\""), "id", "text bla bla");
    }

    @Test
    public void testReplaceVariablesInParameters() {

        ParamsUtility paramsUtility = new ParamsUtility();
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

    private void checkParams(String[] parts, String... expectedParts) {

        Assert.assertEquals(parts.length, expectedParts.length);

        for (int i = 0; i < expectedParts.length; i++) {
            Assert.assertEquals(expectedParts[i], parts[i]);
        }
    }

}
