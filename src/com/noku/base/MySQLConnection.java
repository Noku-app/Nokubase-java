package com.noku.base;

public interface MySQLConnection {
    public void query(String query);
    public String getError();
}
