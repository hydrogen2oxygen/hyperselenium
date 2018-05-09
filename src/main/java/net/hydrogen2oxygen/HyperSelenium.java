package net.hydrogen2oxygen;

import net.hydrogen2oxygen.configuration.RunArguments;

public class HyperSelenium {

    private static RunArguments runArguments;

    public static void main(String [] args) {
        System.out.println("Welcome to HyperSelenium!");

        runArguments = new RunArguments(args);
        System.out.println(runArguments.getKeyValues().toString());
    }
}