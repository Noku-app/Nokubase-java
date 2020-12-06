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

public abstract class DataBaseLink<T extends ResultProvider> {
    protected MySQLConnection<T> connection;
    protected CredentialProvider provider;
    public DataBaseLink(){ }

    /**
     * Initializes a MySQL connection using the parameters from the provided {@link CredentialProvider}
     * @param connection your custom {@link MySQLConnection} implementation.
     * @param provider your custom {@link CredentialProvider} implementation.
     */
    public final void init(MySQLConnection<T> connection, CredentialProvider provider){
        this.provider = provider;
        this.connection = connection;
    }

    /**
     * Connects Link to you custom {@link MySQLConnection} implementation.
     * @return true if successfully connected, false if not.
     */
    public final boolean connect(){
        return this.connection.connect(provider);
    }

    /**
     * Selects columns in provided field list.
     * @param fields columns to be selected.
     * @param table table to select fields from.
     * @param condition condition to check for in each row.
     * @return an instance of your custom {@link ResultProvider} implementation.
     */
    public abstract T select(String[] fields, String table, Condition condition);

    /**
     * Updates columns in provided field list with their values.
     * @param fields columns and values to be updated.
     * @param table table to update fields from.
     * @param condition condition to check for in each row.
     * @return true if operation was successful.
     */
    public abstract boolean update(ColumnValuePair[] fields, String table, Condition condition);

    /**
     * Insert columns in provided field list with their values into a new row.
     * @param fields columns and values to be inserted.
     * @param table table to insert new row into.
     * @return true if operation was successful.
     */
    public abstract boolean insert(ColumnValuePair[] fields, String table);

    /**
     * Deletes all rows that meet the condition.
     * @param table table to have rows deleted from.
     * @param condition condition to be met.
     * @return true if operation was successful.
     */
    public abstract boolean delete(String table, Condition condition);

    /**
     * Performs a custom query.
     * @param customQuery You custom {@link Query} implementation.
     * @return an instance of your custom {@link ResultProvider} implementation.
     */
    public abstract T custom(Query customQuery);
}
