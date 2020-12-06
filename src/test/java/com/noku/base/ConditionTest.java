package com.noku.base;

import junit.framework.TestCase;

public class ConditionTest extends TestCase {
    static Condition condition = new Condition("id", "23");
    public void testBuildPrepared() {
        String pre = condition.buildPrepared();
        String expected = "?='?'";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + pre);
        assertEquals(pre, expected);
    }

    public void testPreparedValues() {
        String[] pre = condition.preparedValues();
        String[] expected = new String[]{"id", "23"};

        for(int i = 0; i < pre.length; i++){
            System.out.println("Build value expects parameter" + i + " to be: " + expected[i]);
            System.out.println("Build value obtained parameter" + i + " to be: " + pre[i]);
            assertEquals(pre[i], expected[i]);
        }
    }
}