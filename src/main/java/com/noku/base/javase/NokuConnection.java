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
import com.noku.base.Query;

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

            connection = DriverManager.getConnection(provider.getHost() + database, provider.getUsername(), provider.getPassword());
            connection.setAutoCommit(false);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public NokuResult query(Query q) {
        int parameter = 0;
        ResultSet res = null;
        try {
            PreparedStatement stat = connection.prepareStatement(q.buildPrepared());
            String[] parameters = q.getParameters();
            char[] parameterTypes = q.getParameterTypes().toCharArray();

            for(int i = 0; i < parameters.length; i++){
                parameter = i;
                switch (parameterTypes[i]){
                    default:
                    case 's': stat.setString(i, parameters[i]); break;
                    case 'i': stat.setInt(i, Integer.parseInt(parameters[i])); break;
                    case 'b': stat.setBoolean(i, Boolean.parseBoolean(parameters[i])); break;
                }
            }
            res = stat.executeQuery();
            connection.commit();

            if(q.getType() == Query.Type.SELECT || q.getType() == Query.Type.CUSTOM) {
                return new NokuResult(q.getColumns(), q.getColumnTypes(), res);
            } else {
                boolean result = false;
                if(res == null) return new NokuResult(false);
                else {
                    if(q.getType() != Query.Type.UPDATE) result = res.rowUpdated();
                    if(q.getType() != Query.Type.DELETE) result = res.rowUpdated();
                    if(q.getType() != Query.Type.INSERT) result = res.rowInserted();
                }
                return new NokuResult(result);
            }

        } catch (SQLTimeoutException e){
            this.error = "SQL Server timed out.";
        } catch (SQLException e){
            this.error = e.getSQLState();
        } catch (NumberFormatException e){
            this.error = "Invalid input for parameter: " + parameter;
        }

        return new NokuResult(false);
    }


    public String getError() {
        return error;
    }
}
