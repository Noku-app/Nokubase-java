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

public class ColumnValuePair {
    public final String column;
    public String value;

    /**
     * Creates a column-value pair for use in queries.
     * @param column name of the table's column
     * @param value value of the column
     */
    public ColumnValuePair(String column, String value){
        this.column = column;
        this.value = value;
    }

    /**
     * Converts this {@link ColumnValuePair} into its MySQL snippet.
     * @return MySQL Snippet.
     */
    public String toString() {
        return column + "='" + value + "'";
    }
}
