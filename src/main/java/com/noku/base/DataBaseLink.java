package com.noku.base;

import java.util.Properties;

public abstract class DataBaseLink<T extends ResultProvider> {
    protected MySQLConnection<T> connection;
    public DataBaseLink(MySQLConnection<T> connection, CredentialProvider provider){
        this.connection = connection;
        this.connection.connect(provider);
    }

    public abstract T select(String[] fields, String table, Condition condition);
    public abstract T update(ColumnValuePair[] fields, String table, Condition condition);
    public abstract T insert(ColumnValuePair[] fields, String table, Condition condition);
    public abstract T delete(String table, Condition condition);
}
