/*
 * Copyright (c) 2020 Xemplar Softworks LLC (https://xemplarsoft.com)
 * Copyright (c) 2020 Noku App
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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