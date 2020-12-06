package com.noku.base.javase;

import com.noku.base.CredentialProvider;
import com.noku.base.MySQLConnection;
import com.noku.base.Query;

import java.sql.*;

public final class JDBCConnection implements MySQLConnection<JDBCResult> {
    private Connection connection;
    private String database, error;

    public void connect(CredentialProvider provider) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.database = provider.getDatabase();
            connection = DriverManager.getConnection(provider.getHost(), provider.getUsername(), provider.getPassword());
            connection.setAutoCommit(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public JDBCResult query(Query q) {
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

            if(q.getType() != Query.Type.SELECT){
                boolean result = false;
                if(res == null) return new JDBCResult(false);
                else {
                    if(q.getType() != Query.Type.UPDATE) result = res.rowUpdated();
                    if(q.getType() != Query.Type.DELETE) result = res.rowUpdated();
                    if(q.getType() != Query.Type.INSERT) result = res.rowInserted();
                }
                return new JDBCResult(result);
            }
            else return new JDBCResult(q.getColumns(), q.getColumnTypes(), res);

        } catch (SQLTimeoutException e){
            this.error = "SQL Server timed out.";
        } catch (SQLException e){
            this.error = e.getSQLState();
        } catch (NumberFormatException e){
            this.error = "Invalid input for parameter: " + parameter;
        }

        return new JDBCResult(false);
    }


    public String getError() {
        return error;
    }
}
