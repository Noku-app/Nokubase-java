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

package com.noku.base;

public class ResultRow {
    public ColumnValuePair[] columns;

    /**
     * Creates a new instance with provided columns.
     * @param columns to be inserted into the new instance.
     */
    public ResultRow(ColumnValuePair... columns){
        if(columns == null) columns = new ColumnValuePair[0];
        this.columns = columns;
    }

    /**
     * Creates an empty instance.
     */
    public ResultRow(){
        this.columns = new ColumnValuePair[0];
    }

    /**
     * Returns value from column name provided.
     * @param column name of column
     * @return value of column
     */
    public String getValueFrom(String column){
        for(int i = 0; i < columns.length; i++){
            if(column.equals(columns[i].column)) return columns[i].value;
        }
        return null;
    }

    /**
     * Adds a column to the row, internal use only.
     * @param pair column and value to add.
     */
    public void addColumn(ColumnValuePair pair){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + 1];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        ret[ret.length - 1] = pair;
        this.columns = ret;
    }

    /**
     * Add columns to the row, internal use only.
     * @param pairs columns and values to add.
     */
    public void addColumns(ColumnValuePair... pairs){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + pairs.length];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        System.arraycopy(pairs, 0, ret, columns.length, pairs.length);
        this.columns = ret;
    }
}
