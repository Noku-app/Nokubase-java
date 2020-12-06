package com.noku.base;

public class ResultRow {
    public ColumnValuePair[] columns;

    public ResultRow(ColumnValuePair... columns){
        if(columns == null) columns = new ColumnValuePair[0];
        this.columns = columns;
    }

    public ResultRow(){
        this.columns = new ColumnValuePair[0];
    }

    public String getValueFrom(String column){
        for(int i = 0; i < columns.length; i++){
            if(column.equals(columns[i].column)) return columns[i].value;
        }
        return null;
    }

    public void addColumn(ColumnValuePair pair){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + 1];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        ret[ret.length - 1] = pair;
        this.columns = ret;
    }

    public void addColumns(ColumnValuePair... pairs){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + pairs.length];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        System.arraycopy(pairs, 0, ret, columns.length, pairs.length);
        this.columns = ret;
    }
}
