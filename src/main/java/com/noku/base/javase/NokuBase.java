package com.noku.base.javase;

import com.noku.base.ColumnValuePair;
import com.noku.base.Condition;
import com.noku.base.DataBaseLink;

import java.util.Properties;

public class NokuBase extends DataBaseLink<JDBCResult> {
    public NokuBase(JDBCConnection connection, Properties properties){
        super(connection, new PropertyCredentialProvider(properties));
    }

    public JDBCResult select(String[] fields, String table, Condition condition) {
        return null;
    }

    public JDBCResult update(ColumnValuePair[] fields, String table, Condition condition) {
        return null;
    }

    public JDBCResult insert(ColumnValuePair[] fields, String table, Condition condition) {
        return null;
    }

    public JDBCResult delete(String table, Condition condition) {
        return null;
    }
}
