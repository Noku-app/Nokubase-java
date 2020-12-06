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
