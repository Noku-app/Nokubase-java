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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConditionSet extends Condition {
    protected Condition[] conditions;
    protected Operator[] operators;

    /**
     * Creates a set of conditions to be met with provided condition array.
     * @param conditions to be merged into one condition.
     * @param operator how to link each condition together.
     */
    public ConditionSet(Condition[] conditions, Operator operator){
        if(operator == null) operator = Operator.AND;
        this.operators = new Operator[conditions.length - 1];
        this.conditions = new Condition[conditions.length];

        System.arraycopy(conditions, 0, this.conditions, 0, conditions.length);
        Arrays.fill(operators, operator);
    }

    public String buildPrepared() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for(int i = 0; i < conditions.length; i++){
            builder.append("(").append(conditions[i].buildPrepared()).append(")");
            if(i < (conditions.length - 1)) builder.append(" ").append(operators[i].op).append(" ");
        }
        builder.append(")");
        return builder.toString();
    }

    public String buildFilled(){
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for(int i = 0; i < conditions.length; i++){
            builder.append("(").append(conditions[i].buildFilled()).append(")");
            if(i < (conditions.length - 1)) builder.append(" ").append(operators[i].op).append(" ");
        }
        builder.append(")");
        return builder.toString();
    }

    public String[] preparedValues() {
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i < conditions.length; i++){
            values.addAll(Arrays.asList(conditions[i].preparedValues()));
        }
        String[] arr = new String[values.size()];
        return values.toArray(arr);
    }

    public int getParameterCount(){
        int ret = 0;
        for(int i = 0; i < conditions.length; i++){
            ret += conditions[i].getParameterCount();
        }
        return ret;
    }

    public enum Operator{
        AND("AND"),
        OR("OR");

        public final String op;
        Operator(String op){
            this.op = op;
        }
    }
}
