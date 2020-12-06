/*
 * Copyright 2020 Xemplar Softworks LLC (https://xemplarsoft.com)
 * Copyright 2020 Noku App
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.database = provider.getDatabase();
            connection = DriverManager.getConnection(provider.getHost(), provider.getUsername(), provider.getPassword());
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
