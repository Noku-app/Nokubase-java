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

public interface MySQLConnection<T extends ResultProvider> {
    /**
     * Set credentials and database to use with this connection and connect to it.
     * @param provider parameter to obtain credentials from.
     */
    public boolean connect(CredentialProvider provider);
    
    /**
     * Send a query to the connected MySQL database.
     * @param s the query string.
     * @param params parameters to insert into the query
     * @return your custom {@link ResultProvider} implementation.
     */
    public T query(String s, String... params);
    
    /**
     * Send a query to the connected MySQL database.
     * @param s the query string.
     * @return your custom {@link ResultProvider} implementation.
     */
    public T query(String s);

    /**
     * Gets the last error from the connection.
     * @return error message.
     */
    public String getError();
}
