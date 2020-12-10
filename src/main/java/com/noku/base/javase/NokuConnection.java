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

import com.noku.base.CredentialProvider;
import com.noku.base.MySQLConnection;

import java.sql.*;

public final class NokuConnection implements MySQLConnection<NokuResult> {
    private Connection connection;
    private String database, error;

    public boolean connect(CredentialProvider provider) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            this.database = provider.getDatabase();
            if(!provider.getHost().endsWith("/")) this.database = "/" + this.database;
            this.database += "?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
            connection = DriverManager.getConnection(provider.getHost() + database, provider.getUsername(), provider.getPassword());
            System.out.println(provider.getHost() + database);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public NokuResult query(String s){
        String checker = s.toUpperCase().trim();
        try{
            Statement stat = this.connection.createStatement();
            stat.closeOnCompletion();
            if(checker.startsWith("SELECT")) new NokuResult(stat.executeQuery(s), true);
            else if(checker.startsWith("UPDATE")) return new NokuResult(stat.executeUpdate(s));
            else if(checker.startsWith("INSERT")) return new NokuResult(stat.executeUpdate(s));
            else if(checker.startsWith("DELETE")) return new NokuResult(stat.executeUpdate(s));
            else return new NokuResult(stat.execute(s));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public NokuResult query(String s, String... params){
        try{
            PreparedStatement stat = this.connection.prepareStatement(s);
            for(int i = 0; i < params.length; i++){
                stat.setString(i + 1, params[i]);
            }
            if(s.startsWith("SELECT")) return new NokuResult(stat.executeQuery());
            else if(s.startsWith("UPDATE")) return new NokuResult(stat.executeUpdate());
            else if(s.startsWith("INSERT")) return new NokuResult(stat.executeUpdate());
            else if(s.startsWith("DELETE")) return new NokuResult(stat.executeUpdate());
            return new NokuResult(false, -1 << 10, "Method not valid.");
        } catch (SQLException e){
            e.printStackTrace();
            return new NokuResult(false, e.getErrorCode(), e.getMessage());
        }
    }

    public String getError() {
        return error;
    }
}
