package com.noku.base;

public class ColumnValuePair {
    public final String column;
    public String value;
    public ColumnValuePair(String column, String value){
        this.column = column;
        this.value = value;
    }

    public String toString() {
        return column + "='" + value + "'";
    }
}
