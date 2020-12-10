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
