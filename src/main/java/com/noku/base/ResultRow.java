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
    /* package */ void addColumn(ColumnValuePair pair){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + 1];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        ret[ret.length - 1] = pair;
        this.columns = ret;
    }

    /**
     * Add columns to the row, internal use only.
     * @param pairs columns and values to add.
     */
    /* package */ void addColumns(ColumnValuePair... pairs){
        ColumnValuePair[] ret = new ColumnValuePair[columns.length + pairs.length];
        System.arraycopy(this.columns, 0, ret, 0, this.columns.length);
        System.arraycopy(pairs, 0, ret, columns.length, pairs.length);
        this.columns = ret;
    }
}
