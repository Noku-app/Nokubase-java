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
