package com.noku.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConditionSet extends Condition {
    protected Condition[] conditions;
    protected Operator[] operators;
    public ConditionSet(Condition[] conditions, Operator operator){
        if(operator == null) operator = Operator.AND;
        this.operators = new Operator[conditions.length - 1];
        this.conditions = new Condition[conditions.length];

        System.arraycopy(conditions, 0, this.conditions, 0, conditions.length);
        Arrays.fill(operators, operator);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for(int i = 0; i < conditions.length; i++){
            builder.append(conditions[i].toString());
            if(i < (conditions.length - 1)) builder.append(" ").append(operators[i].op).append(" ");
        }
        builder.append(")");

        return builder.toString();
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
