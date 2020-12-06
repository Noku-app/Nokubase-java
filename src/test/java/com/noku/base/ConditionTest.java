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