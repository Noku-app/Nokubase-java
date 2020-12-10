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

import java.util.Stack;

/**
 * The type Data base link.
 *
 * @param <T> the type parameter
 */
public abstract class DataBaseLink<T extends ResultProvider> {
    /**
     * The Messages.
     */
    protected Stack<ResultMessage> messages = new Stack<>();
    /**
     * The Connection.
     */
    protected MySQLConnection<T> connection;
    /**
     * The Provider.
     */
    protected CredentialProvider provider;

    /**
     * Instantiates a new Data base link.
     */
    public DataBaseLink(){ }

    /**
     * Initializes a MySQL connection using the parameters from the provided {@link CredentialProvider}
     *
     * @param connection your custom {@link MySQLConnection} implementation.
     * @param provider   your custom {@link CredentialProvider} implementation.
     */
    protected final void init(MySQLConnection<T> connection, CredentialProvider provider){
        this.provider = provider;
        this.connection = connection;
    }

    /**
     * Connects Link to you custom {@link MySQLConnection} implementation.
     *
     * @return true if successfully connected, false if not.
     */
    public final boolean connect(){
        return this.connection.connect(provider);
    }

    /**
     * Selects columns in provided field list.
     *
     * @param table     table to select fields from.
     * @param condition condition to check for in each row.
     * @param fields    columns to be selected.
     * @return an instance of your custom {@link ResultProvider} implementation.
     */
    public abstract T select(String table, Condition condition, String... fields);

    /**
     * Updates columns in provided field list with their values.
     *
     * @param table     table to update fields from.
     * @param condition condition to check for in each row.
     * @param fields    columns and values to be updated.
     * @return true if operation was successful.
     */
    public abstract boolean update(String table, Condition condition, ColumnValuePair... fields);

    /**
     * Insert columns in provided field list with their values into a new row.
     *
     * @param table  table to insert new row into.
     * @param fields columns and values to be inserted.
     * @return true if operation was successful.
     */
    public abstract boolean insert(String table, ColumnValuePair... fields);

    /**
     * Deletes all rows that meet the condition.
     *
     * @param table     table to have rows deleted from.
     * @param condition condition to be met.
     * @return true if operation was successful.
     */
    public abstract boolean delete(String table, Condition condition);

    /**
     * Performs a custom query.
     *
     * @param customQuery You custom sql query
     * @return an instance of your custom {@link ResultProvider} implementation.
     */
    public abstract T query(String customQuery);

    /**
     * Performs a custom prepared query.
     *
     * @param customQuery You custom sql query
     * @param params      params to insert into query
     * @return an instance of your custom {@link ResultProvider} implementation.
     */
    public abstract T query(String customQuery, String... params);

    /**
     * Returns the last error put into the buffer;
     *
     * @return Error String.
     */
    public final String getError(){
        return connection.getError();
    }

    /**
     * Adds a {@link ResultMessage} to the message stack.
     *
     * @param message to be added to the stack
     */
    protected final void pushMessage(ResultMessage message){
        messages.push(message);
    }

    /**
     * Adds a {@link ResultMessage} to the message stack.
     *
     * @param code    to be used to create a {@link ResultMessage} to be added to the stack
     * @param message to be used to create a {@link ResultMessage} to be added to the stack
     */
    protected final void pushMessage(final int code, final String message){
        this.pushMessage(new ResultMessage(code, message));
    }

    /**
     * Peeks the last message in the stacks without removing it.
     *
     * @return last message added to stack.
     */
    public ResultMessage peekMessage(){
        return messages.peek();
    }

    /**
     * Pops the last message in the stacks and removes it.
     *
     * @return last message added to stack.
     */
    public ResultMessage popMessage(){
        return messages.pop();
    }
}
