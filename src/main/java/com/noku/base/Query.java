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

import java.util.Queue;

public abstract class Query {
    protected final Condition condition;
    protected String[] fields;
    protected String table;
    protected final Type t;

    /**
     * Default Constructor for queries.
     * @param t type of query. If you are making your own class, set to CUSTOM
     * @param table table the query will be executed in.
     * @param condition condition to be met.
     */
    private Query(Type t, String table, Condition condition){
        this.t = t;
        this.table = table;
        this.condition = condition;
    }

    /**
     * Builds SQL snippet with empty parameters.
     * @return SQL snippet.
     */
    public abstract String buildPrepared();

    /**
     * Builds SQL snippet with parameters.
     * @return SQL snippet.
     */
    public abstract String buildFilled();

    /**
     * Gets all parameter types. String are s, Ints are i and Booleans are b. ex: "sss".
     * @return String with each char representing the data type of each parameter.
     */
    public abstract String getParameterTypes();

    /**
     * Gets all parameter values.
     * @return an array of parameter values.
     */
    public abstract String[] getParameters();

    /**
     * Gets all columns.
     * @return a string-array containing all column names.
     */
    public String[] getColumns(){
        return fields;
    }

    /**
     * Gets all field types. String are s, Ints are i and Booleans are b. ex: "sss".
     * @return String with each char representing the data type of each field.
     */
    public String getColumnTypes(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < fields.length; i++){
            builder.append("s");
        }
        return builder.toString();
    }

    /**
     * Gets the type of Query
     * @return the type of query.
     */
    public final Type getType(){
        return t;
    }

    /**
     * Creates a new select query from provided arguments.
     * @return a new instance of SelectQuery
     */
    public static SelectQuery select(String[] fields, String table, Condition condition){
        return new SelectQuery(fields, table, condition);
    }

    /**
     * Creates a new update query from provided arguments.
     * @return a new instance of UpdateQuery
     */
    public static UpdateQuery update(ColumnValuePair[] values, String table, Condition condition){
        return new UpdateQuery(values, table, condition);
    }

    /**
     * Creates a new insert query from provided arguments.
     * @return a new instance of InsertQuery
     */
    public static InsertQuery insert(ColumnValuePair[] values, String table){
        return new InsertQuery(values, table);
    }

    /**
     * Creates a new delete query from provided arguments.
     * @return a new instance of DeleteQuery
     */
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
        DELETE,
        CUSTOM
    }
}
