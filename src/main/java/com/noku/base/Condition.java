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

public class Condition {
    protected ConditionType type;
    protected String name, var;

    /**
     * Creates a condition for use in queries.
     * @param name column name to check.
     * @param var value to check for.
     * @param type how to evaluate the condition.
     */
    public Condition(String name, String var, ConditionType type){
        this.name = name;
        this.var = var;
        this.type = type;
    }

    /**
     * Creates a condition for use in queries with {@link ConditionType} EQUALS.
     * @param name column name to check.
     * @param var value to check for.
     */
    public Condition(String name, String var){
        this(name, var, ConditionType.EQUALS);
    }

    /**
     * Creates a blank condition.
     */
    protected Condition(){}

    /**
     * Builds SQL snippet with empty parameters.
     * @return SQL snippet.
     */
    public String buildPrepared(){
        return "?" + type.operator + "'?'";
    }

    /**
     * Builds SQL snippet with parameters.
     * @return SQL snippet.
     */
    public String buildFilled(){
        return name + type.operator + "'" + var + "'";
    }

    /**
     * Gets all values to be bound to parameters.
     * @return SQL snippet.
     */
    public String[] preparedValues(){
        return new String[]{name, var};
    }

    /**
     * Gets the number of total parameters
     * @return number of parameters.
     */
    public int getParameterCount(){
        return 2;
    }
}
