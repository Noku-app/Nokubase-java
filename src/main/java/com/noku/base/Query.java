package com.noku.base;

import java.util.Queue;

public abstract class Query {
    protected final Condition condition;
    protected String[] fields;
    protected String table;
    protected final Type t;

    private Query(Type t, String table, Condition condition){
        this.t = t;
        this.table = table;
        this.condition = condition;
    }

    public abstract String buildPrepared();
    public abstract String buildFilled();
    public abstract String getParameterTypes();
    public abstract String[] getParameters();
    public String[] getColumns(){
        return fields;
    }

    public String getColumnTypes(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < fields.length; i++){
            builder.append("s");
        }
        return builder.toString();
    }

    public final Type getType(){
        return t;
    }

    public static SelectQuery select(String[] fields, String table, Condition condition){
        return new SelectQuery(fields, table, condition);
    }

    public static UpdateQuery update(ColumnValuePair[] values, String table, Condition condition){
        return new UpdateQuery(values, table, condition);
    }

    public static InsertQuery insert(ColumnValuePair[] values, String table){
        return new InsertQuery(values, table);
    }

    public static DeleteQuery delete(String table, Condition condition){
        return new DeleteQuery(table, condition);
    }

    public static class SelectQuery extends Query{
        private SelectQuery(String[] fields, String table, Condition condition){
            super(Type.SELECT, table, condition);
            this.fields = fields;
        }

        public String buildPrepared() {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT ");
            for(int i = 0; i < fields.length; i++){
                builder.append("?");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(" FROM ").append("`").append(table).append("` WHERE ");
            builder.append(condition.buildPrepared());
            return builder.toString();
        }

        public String buildFilled() {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT ");
            for(int i = 0; i < fields.length; i++){
                builder.append(fields[i]);
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(" FROM ").append("`").append(table).append("` WHERE ");
            builder.append(condition.buildFilled());
            return builder.toString();
        }

        public String getParameterTypes() {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < (fields.length + condition.getParameterCount()); i++) builder.append("s");
            return builder.toString();
        }

        public String[] getParameters() {
            String[] ret = new String[fields.length + condition.getParameterCount()];
            String[] condParams = condition.preparedValues();
            System.arraycopy(fields, 0, ret, 0, fields.length);
            System.arraycopy(condParams, 0, ret, fields.length, condParams.length);
            return ret;
        }
    }

    public static class UpdateQuery extends Query{
        private ColumnValuePair[] values;
        private UpdateQuery(ColumnValuePair[] values, String table, Condition condition){
            super(Type.UPDATE, table, condition);
            String[] fields = new String[values.length];
            for(int i = 0; i < fields.length; i++){
                fields[i] = values[i].column;
            }
            this.fields = fields;
            this.values = values;
        }

        public String buildPrepared() {
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ").append("`").append(table).append("` SET ");
            for(int i = 0; i < values.length; i++){
                builder.append("`?` = '?'");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(" WHERE ");
            builder.append(condition.buildPrepared());
            return builder.toString();
        }

        public String buildFilled() {
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ").append("`").append(table).append("` SET ");
            for(int i = 0; i < values.length; i++){
                builder.append("`").append(values[i].column).append("` = '").append(values[i].value).append("'");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(" WHERE ");
            builder.append(condition.buildFilled());
            return builder.toString();
        }

        public String getParameterTypes() {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < (fields.length + condition.getParameterCount()); i++) builder.append("s");
            return builder.toString();
        }

        public String[] getParameters() {
            String[] ret = new String[fields.length + condition.getParameterCount()];
            String[] condParams = condition.preparedValues();
            System.arraycopy(fields, 0, ret, 0, fields.length);
            System.arraycopy(condParams, 0, ret, fields.length, condParams.length);
            return ret;
        }
    }

    public static class InsertQuery extends Query{
        private ColumnValuePair[] values;
        private InsertQuery(ColumnValuePair[] values, String table){
            super(Type.INSERT, table, null);
            String[] fields = new String[values.length];
            for(int i = 0; i < fields.length; i++){
                fields[i] = values[i].column;
            }
            this.fields = fields;
            this.values = values;
        }

        public String buildPrepared() {
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO ").append("`").append(table).append("` (");
            for(int i = 0; i < values.length; i++){
                builder.append("`?`");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(") VALUES (");
            for(int i = 0; i < values.length; i++){
                builder.append("'?'");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(")");
            return builder.toString();
        }

        public String buildFilled() {
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO ").append("`").append(table).append("` (");
            for(int i = 0; i < values.length; i++){
                builder.append("`").append(values[i].column).append("`");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(") VALUES (");
            for(int i = 0; i < values.length; i++){
                builder.append("'").append(values[i].value).append("'");
                if(i < (fields.length - 1)) builder.append(", ");
            }
            builder.append(")");
            return builder.toString();
        }

        public String getParameterTypes() {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < (fields.length + condition.getParameterCount()); i++) builder.append("s");
            return builder.toString();
        }

        public String[] getParameters() {
            String[] ret = new String[fields.length + condition.getParameterCount()];
            String[] condParams = condition.preparedValues();
            System.arraycopy(fields, 0, ret, 0, fields.length);
            System.arraycopy(condParams, 0, ret, fields.length, condParams.length);
            return ret;
        }
    }

    public static class DeleteQuery extends Query{
        private DeleteQuery(String table, Condition condition){
            super(Type.DELETE, table, condition);
            this.fields = new String[0];
        }

        public String buildPrepared() {
            return "DELETE FROM " + "`" + table + "` WHERE " + condition.buildPrepared();
        }

        public String buildFilled() {
            return "DELETE FROM " + "`" + table + "` WHERE " + condition.buildFilled();
        }

        public String getParameterTypes() {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < (condition.getParameterCount()); i++) builder.append("s");
            return builder.toString();
        }

        public String[] getParameters() {
            return condition.preparedValues();
        }
    }

    public enum Type{
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }
}
