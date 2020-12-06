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

import com.noku.base.ColumnValuePair;
import com.noku.base.ResultProvider;
import com.noku.base.ResultRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class NokuResult implements ResultProvider {
    private ArrayList<ResultRow> rows = new ArrayList<>();
    private boolean successful, bool;
    public NokuResult(String[] cols, String types_defs, ResultSet set){
        char[] types = types_defs.toCharArray();
        try {
            while (set.next()) {
                ResultRow ret = new ResultRow();
                for(int i = 0; i < cols.length; i++){
                    switch (types[i]){
                        case 'i': ret.addColumn(new ColumnValuePair(cols[i], set.getInt(cols[i]) + "")); continue;
                        case 's': ret.addColumn(new ColumnValuePair(cols[i], set.getString(cols[i]))); continue;
                        case 'b': ret.addColumn(new ColumnValuePair(cols[i], Boolean.toString(set.getBoolean(cols[i])))); continue;
                    }
                }
                rows.add(ret);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public NokuResult(boolean successful){
        this.bool = true;
        this.successful = successful;
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
}
