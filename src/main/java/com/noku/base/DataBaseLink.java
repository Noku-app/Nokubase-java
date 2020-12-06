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

public abstract class DataBaseLink<T extends ResultProvider> {
    protected MySQLConnection<T> connection;
    protected CredentialProvider provider;
    public DataBaseLink(){ }

    /**
     * Initializes a MySQL connection using the parameters from the provided {@link CredentialProvider}
     * @param connection your custom {@link MySQLConnection} implementation.
     * @param provider your custom {@link CredentialProvider} implementation.
     */
    protected final void init(MySQLConnection<T> connection, CredentialProvider provider){
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
