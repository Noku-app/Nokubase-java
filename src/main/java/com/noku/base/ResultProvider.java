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

public interface ResultProvider {
    /**
     * Checks if result is a boolean or a {@link ResultRow}.
     * @return true if the result of the query is a boolean.
     */
    public boolean isBool();

    /**
     * Checks to see if result is valid or not.
     * @return true if the result was successful.
     */
    public boolean isSuccessful();

    /**
     * Gets the number of rows from query.
     * @return number of rows returned from query.
     */
    public int getRowCount();

    /**
     * Gathers all {@link ResultRow} in result and returns them.
     * @return an array of {@link ResultRow} from query.
     */
    public ResultRow[] getRows();

    /**
     * Gathers a row at specified index
     * @param index of row
     * @return the {@link ResultRow} at the specified index.
     */
    public ResultRow getRow(int index);
}
