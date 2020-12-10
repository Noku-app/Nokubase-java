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
import com.noku.base.ResultMessage;
import com.noku.base.ResultProvider;
import com.noku.base.ResultRow;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public final class NokuResult implements ResultProvider {
    private ArrayList<ResultRow> rows = new ArrayList<>();
    private boolean successful, bool, row;
    private int rowCount, code;
    private String message;

    public NokuResult(ResultSet set){
        try {
            ResultSetMetaData md = set.getMetaData();
            boolean isEmpty = true;
            while(set.next()) {
                isEmpty = false;
                System.out.println("Processing Result Row: " + rows.size());
                ResultRow ret = new ResultRow();
                int colCount = md.getColumnCount();
                for(int i = 1; i <= colCount; i++){
                    System.out.println("  Col Name: " + md.getColumnName(i));
                    System.out.println("  Col Type: " + md.getColumnTypeName(i));
                    System.out.println("  Col Value: " + set.getString(i));
                    System.out.println("  Col Table: " + md.getTableName(i));
                    ret.addColumn(new ColumnValuePair(md.getColumnName(i), set.getString(i)));
                }
                rows.add(ret);
            }
            this.successful = !isEmpty;
            if(isEmpty){
                if(set.getWarnings() != null) this.message = set.getWarnings().getMessage();
            }
            set.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public NokuResult(boolean successful){
        this.bool = true;
        this.successful = successful;
    }

    public NokuResult(boolean successful, int code, String message){
        this.successful = successful;
        this.message = message;
        this.code = code;
    }

    public NokuResult(int rowCount){
        this.row = true;
        this.successful = rowCount > 0;
        this.rowCount = rowCount;
    }

    public boolean isBool(){
        return bool;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public int getRowCount(){
        return this.rows.size();
    }

    public ResultRow[] getRows(){
        ResultRow[] ret = new ResultRow[getRowCount()];
        ret = rows.toArray(ret);
        return ret;
    }

    public ResultRow getRow(int index){
        return rows.get(index);
    }

    public String getMessage(){
        return message;
    }
    public int getCode(){
        return code;
    }

    public ResultMessage asResultMessage(){
        return new ResultMessage(code, message);
    }
}
