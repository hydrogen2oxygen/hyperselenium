package net.hydrogen2oxygen.hyperselenium.services;

import org.junit.Assert;
import org.junit.Test;

public class ParamsUtilityTest {

    @Test
    public void test() {

        ParamsUtility paramsUtility = new ParamsUtility();

        checkParams(paramsUtility.splitParams("id"),"id");
        checkParams(paramsUtility.splitParams("a id"),"a","id");
        checkParams(paramsUtility.splitParams("id \"text bla bla\""),"id","text bla bla");

        checkParams(paramsUtility.getParamsFromCommandLine("click id \"text bla bla\""),"id","text bla bla");
    }

    private void checkParams(String[] parts, String ... expectedParts) {

        Assert.assertEquals(parts.length, expectedParts.length);

        for (int i=0; i<expectedParts.length; i++){
            Assert.assertEquals(expectedParts[i], parts[i]);
        }
    }

}
