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

package com.noku.base.javase;

import com.noku.base.Condition;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");

        Properties props = new Properties();
        props.load(new FileInputStream(new File("db.properties")));
        NokuBase base = new NokuBase(props);
        boolean connected = base.connect();
        System.out.println("Connected: " + connected);

        if(connected) {
            base.initTables();
            
            boolean result = base.delete("testdb_codes", new Condition("id", 13));
            //boolean result = base.update("testdb_user", new Condition("id", "1"), new ColumnValuePair("data", "[]"));
            //NokuResult result = base.select("testdb_user", new Condition("1", "1"), "email", "achievements");
            //NokuResult result = base.query("SELECT * FROM testdb_user WHERE ?=?;", "1", "1");

            //if(result.isSuccessful()) {
            //    ResultRow curr = result.getRow(0);
            //    System.out.println("Email: " + curr.getValueFrom("email"));
            //    System.out.println("Achievements: " + curr.getValueFrom("achievements"));
            //} else {
            //    System.out.println("Error: " + base.getError());
            //    System.out.println("Error: " + result.getError());
            //}
            System.out.println("Result: " + result);
        }
    }
}
