package com.noku.base;

public enum ConditionType {
    EQUALS("="),
    GREATER(">"),
    LESSER("<"),
    NOT_EQUAL("!=");

    public final String operator;
    ConditionType(String operator){
        this.operator = operator;
    }
}
