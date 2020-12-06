package com.noku.base;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

    public void testSelect() {
        Query.SelectQuery q = Query.select(new String[]{"id", "user", "email"}, "noku_users", ConditionSetTest.condition);
        String expected = "SELECT ?, ?, ? FROM `noku_users` WHERE ((?='?') AND (?>'?'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildPrepared());
        assertEquals(q.buildPrepared(), expected);

        expected = "SELECT id, user, email FROM `noku_users` WHERE ((uid='73') AND (pid>'23'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildFilled());
        assertEquals(q.buildFilled(), expected);
    }

    public void testUpdate() {
        ColumnValuePair[] pairs = new ColumnValuePair[2];
        pairs[0] = new ColumnValuePair("user", "TheNextGuy");
        pairs[1] = new ColumnValuePair("email", "Roxas240@gmail.com");

        Query.UpdateQuery q = Query.update(pairs, "noku_users", ConditionSetTest.condition);
        String expected = "UPDATE `noku_users` SET `?` = '?', `?` = '?' WHERE ((?='?') AND (?>'?'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildPrepared());
        assertEquals(q.buildPrepared(), expected);

        expected = "UPDATE `noku_users` SET `user` = 'TheNextGuy', `email` = 'Roxas240@gmail.com' WHERE ((uid='73') AND (pid>'23'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildFilled());
        assertEquals(q.buildFilled(), expected);
    }

    public void testInsert() {
        ColumnValuePair[] pairs = new ColumnValuePair[2];
        pairs[0] = new ColumnValuePair("user", "TheNextGuy");
        pairs[1] = new ColumnValuePair("email", "Roxas240@gmail.com");

        Query.InsertQuery q = Query.insert(pairs, "noku_users");
        String expected = "INSERT INTO `noku_users` (`?`, `?`) VALUES ('?', '?')";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildPrepared());
        assertEquals(q.buildPrepared(), expected);

        expected = "INSERT INTO `noku_users` (`user`, `email`) VALUES ('TheNextGuy', 'Roxas240@gmail.com')";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildFilled());
        assertEquals(q.buildFilled(), expected);
    }

    public void testDelete() {
        Query.DeleteQuery q = Query.delete("noku_users", ConditionSetTest.condition);
        String expected = "DELETE FROM `noku_users` WHERE ((?='?') AND (?>'?'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildPrepared());
        assertEquals(q.buildPrepared(), expected);

        expected = "DELETE FROM `noku_users` WHERE ((uid='73') AND (pid>'23'))";
        System.out.println("Build prepared expects: " + expected);
        System.out.println("Build prepared obtained: " + q.buildFilled());
        assertEquals(q.buildFilled(), expected);
    }
}