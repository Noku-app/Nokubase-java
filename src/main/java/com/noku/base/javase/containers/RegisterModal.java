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

import java.util.*;

public class RegisterModal extends DataModal{
    protected final String[] keys = new String[]{
        "email", "uid", "creation_time", "points", "pfp", "age",
        "nsfw", "moderator", "admin", "developer", "gender", "nick",
        "bio", "background_color", "border_color"
    };
    protected Map<String, Object> data = new HashMap<>();
    
    public RegisterModal putValue(String key, Object value){
        if(!Arrays.asList(keys).contains(key)) return null;
        data.put(key, value);
        return this;
    }
    
    public String[] getKeys() {
        return keys;
    }
    
    public Object getValue(String name) {
        Object val = data.get(name);
        if(val == null) return getDefaultValue(name, "");
        else return val;
    }
    
    public Object getDefaultValue(String name, Object def){
        if(name == null) return def;
        switch(name){
            case "creation_time":{
                return System.currentTimeMillis();
            }
            case "points": return 0;
            
            case "age":
            case "bio":
            case "gender":
            case "border_color":
            case "background_color":
            case "pfp": return def;
            
            case "moderator":
            case "admin":
            case "developer":
            case "nsfw": return false;
        }
        
        return def;
    }
}
