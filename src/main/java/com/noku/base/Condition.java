package com.noku.base;

public class Condition {
    protected ConditionType type;
    protected String name, var;

    public Condition(String name, String var, ConditionType type){
        this.name = name;
        this.var = var;
        this.type = type;
    }

    public Condition(String name, String var){
        this(name, var, ConditionType.EQUALS);
    }

    protected Condition(){}

    public String buildPrepared(){
        return "?" + type.operator + "'?'";
    }

    public String buildFilled(){
        return name + type.operator + "'" + var + "'";
    }

    public String[] preparedValues(){
        return new String[]{name, var};
    }

    public int getParameterCount(){
        return 2;
    }

    public String toString() {
        return name + type.operator + "'" + var + "'";
    }
}
