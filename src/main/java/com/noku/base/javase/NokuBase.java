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

import com.noku.base.ColumnValuePair;
import com.noku.base.Condition;
import com.noku.base.DataBaseLink;
import com.noku.base.MySQLConnection;
import com.noku.base.javase.containers.RegisterModal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class NokuBase extends DataBaseLink<NokuResult> {
    /**
     * Create a new instance of {@link NokuBase}
     * @param properties {@link Properties} object to load connection settings from.
     */
    public NokuBase(Properties properties){
        MySQLConnection<NokuResult> connection = new NokuConnection();
        init(connection, new PropertyCredentialProvider(properties));
    }

    /**
     * Creates all tables that should exist for the NokuApp
     */
    public void initTables(){
        String[] tables = new String[]{
            "CREATE TABLE IF NOT EXISTS tokens \n" +
            "    (\n" +
            "        uid INT UNSIGNED NOT NULL, \n" +
            "        secret VARCHAR(60) NOT NULL, \n" +
            "        token VARCHAR(150) NOT NULL\n" +
            "    );",
            "CREATE TABLE IF NOT EXISTS account \n" +
            "    (\n" +
            "        uid INT UNSIGNED NOT NULL, \n" +
            "        email VARCHAR(50) NOT NULL, \n" +
            "        creation_time BIGINT UNSIGNED NOT NULL,\n" +
            "        points INT UNSIGNED NOT NULL,\n" +
            "        pfp VARCHAR(60),\n" +
            "        age INT UNSIGNED,\n" +
            "        nsfw BOOLEAN NOT NULL,\n" +
            "        moderator BOOLEAN NOT NULL,\n" +
            "        admin BOOLEAN NOT NULL,\n" +
            "        developer BOOLEAN NOT NULL,\n" +
            "        gender VARCHAR(8),\n" +
            "        nick VARCHAR(20) NOT NULL,\n" +
            "        bio VARCHAR(500),\n" +
            "        background_color VARCHAR(10),\n" +
            "        border_color VARCHAR(10)\n" +
            "    );"
        };
        
        for(int i = 0; i < tables.length; i++){
            boolean set = query(tables[i]).isSuccessful();
            System.out.println("Table '" + tables[i].substring(tables[i].indexOf("EXISTS") + 7, tables[i].indexOf("\n") - 1) + "' created: " + set);
        }
    }
    
    public boolean registerUser(RegisterModal modal){
        if(modal == null) return false;
        return insert("account", modal.asColumns());
    }
    
    public boolean tokenUser(int uid, String secret, String token){
        return insert("tokens",
            ColumnValuePair.from("uid", uid + ""),
            ColumnValuePair.from("secret", secret),
            ColumnValuePair.from("token", token)
        );
    }
    
    public boolean updateSecret(int uid, String secret){
        return update("tokens", new Condition("uid", uid), ColumnValuePair.from("secret", secret));
    }
    
    public boolean updateToken(int uid, String token){
        return update("tokens", new Condition("uid", uid), ColumnValuePair.from("token", token));
    }
    
    public boolean emailTaken(String email){
        NokuResult result = select("account", new Condition("email", email), "id");
        if(result.isEmpty()) return true;
        else {
            System.out.println("Email Taken by uid: " + result.getRow(0).getValueFrom("id"));
            return false;
        }
    }

    public NokuResult select(String table, Condition condition, String... fields) {
        String query = "SELECT {fields} FROM " + table;
        if(fields == null) return null;
        if(table == null) return null;
        if(condition != null) query += " WHERE {condition}";
        StringBuilder insert = new StringBuilder();
        for(int i = 0; i < fields.length; i++){
            insert.append(fields[i]);
            if(i < (fields.length - 1)) insert.append(", ");
        }
        query = query.replace("{fields}", insert.toString());
        if(condition != null) {
            query = query.replace("{condition}", condition.buildPrepared());
            return connection.query(query, condition.preparedValues());
        } else return connection.query(query);
    }

    public boolean update(String table, Condition condition, ColumnValuePair... fields) {
        String query = "UPDATE " + table + " SET {values}";
        if(fields == null) return false;
        if(table == null) return false;
        if(condition != null) query += " WHERE {condition}";
        ArrayList<String> params = new ArrayList<>();
        StringBuilder insert = new StringBuilder();
        for(int i = 0; i < fields.length; i++){
            insert.append(fields[i].column).append(" = '").append(fields[i].value).append("'");
            if(i < (fields.length - 1)) insert.append(", ");
        }
        query = query.replace("{values}", insert.toString());
        NokuResult set;
        if(condition != null) {
            query = query.replace("{condition}", condition.buildPrepared());
            Collections.addAll(params, condition.preparedValues());
        }
        set = connection.query(query, params.toArray(new String[0]));
        System.out.println("Execute Query: " + query);

        if(set.getCode() != 0) pushMessage(set.asResultMessage());

        return set.isSuccessful();
    }

    public boolean insert(String table, ColumnValuePair... fields) {
        String query = "INSERT INTO " + table + " ({columns}) VALUES ({values})";
        if(fields == null) return false;
        if(table == null) return false;
        ArrayList<String> values = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder params = new StringBuilder();
        for(int i = 0; i < fields.length; i++){
            columns.append(fields[i].column);
            if(i < (fields.length - 1)) columns.append(", ");

            params.append("?");
            if(i < (fields.length - 1)) params.append(", ");

            values.add(fields[i].value);
        }
        query = query.replace("{columns}", columns.toString());
        query = query.replace("{values}", params.toString());
        NokuResult set;
        set = connection.query(query, values.toArray(new String[0]));
        System.out.println("Execute Query: " + query);

        if(set.getCode() != 0) pushMessage(set.asResultMessage());

        return set.isSuccessful();
    }

    @Override
    public boolean delete(String table, Condition condition) {
        String query = "DELETE FROM " + table + " WHERE {condition}";
        if(table == null) return false;
        if(condition == null) return false;

        NokuResult set;
        query = query.replace("{condition}", condition.buildPrepared());
        set = connection.query(query, condition.preparedValues());
        System.out.println("Execute Query: " + query);

        if(set.getCode() != 0) pushMessage(set.asResultMessage());

        return set.isSuccessful();
    }

    public NokuResult query(String s){
        System.out.println("Execute Query: " + s);
        return connection.query(s);
    }

    public NokuResult query(String s, String... params){
        System.out.println("Execute Query: " + s);
        return connection.query(s, params);
    }
}
