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
import com.noku.base.ResultRow;

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
            NokuResult result = base.select(new String[]{"user_id", "player_id"}, "testdb_player", new Condition("id", "1"));
            ResultRow curr = result.getRow(0);
            System.out.println("User ID: " + curr.getValueFrom("user_id"));
            System.out.println("Player ID: " + curr.getValueFrom("player_id"));
        }
    }
}
