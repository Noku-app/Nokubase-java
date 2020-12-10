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

package com.noku.base.javase.containers;

import com.noku.base.ColumnValuePair;

public abstract class DataModal {
    /**
     * Returns the value associated with the provided name
     * @param name key name
     * @return String containing the value stored.
     */
    public abstract Object getValue(String name);
    
    /**
     * Adds a value to the modal and returns itself
     * @param key to reference value with
     * @param value to store
     * @return this instance
     */
    public abstract DataModal putValue(String key, Object value);
    
    /**
     * Creates a list of keys that are valid for this modal.
     * @return String-Array that contains valid keys for this modal.
     */
    public abstract String[] getKeys();
    
    /**
     * Creates a new {@link ColumnValuePair}-Array from the values stored in keys.
     * @return newly created {@link ColumnValuePair}-Array
     */
    public ColumnValuePair[] asColumns(){
        String[] keys = getKeys();
        ColumnValuePair[] ret = new ColumnValuePair[keys.length];
        
        for(int i = 0; i < ret.length; i++){
            ret[i] = ColumnValuePair.from(keys[i], String.valueOf(getValue(keys[i])));
        }
        
        return ret;
    }
    
    /**
     * Default Constructor
     */
    public DataModal(){}
}
