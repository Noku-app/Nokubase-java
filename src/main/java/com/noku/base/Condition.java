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
