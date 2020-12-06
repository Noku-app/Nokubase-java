package com.noku.base;

import junit.framework.TestCase;

public class ConditionSetTest extends TestCase {
    static Condition condition = new ConditionSet(new Condition[]{
        new Condition("uid", "73"),
        new Condition("pid", "23", ConditionType.GREATER),
    }, ConditionSet.Operator.AND);

    public void testBuildPrepared() {
        String pre = condition.buildPrepared();
        String expected = "((?='?') AND (?>'?'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + pre);
        assertEquals(pre, expected);
    }

    public void testPreparedValues() {
        String[] pre = condition.preparedValues();
        String[] expected = new String[]{"uid", "73", "pid", "23"};

        for(int i = 0; i < pre.length; i++){
            System.out.println("Build value expects parameter" + i + " to be: " + expected[i]);
            System.out.println("Build value obtained parameter" + i + " to be: " + pre[i]);
            assertEquals(pre[i], expected[i]);
        }
    }
}