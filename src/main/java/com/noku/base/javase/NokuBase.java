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

package com.noku.base.javase;

import com.noku.base.*;
import java.util.Properties;

public class NokuBase extends DataBaseLink<NokuResult> {
    public NokuBase(Properties properties){
        MySQLConnection<NokuResult> connection = new NokuConnection();
        init(connection, new PropertyCredentialProvider(properties));
    }

    public NokuResult select(String[] fields, String table, Condition condition) {
        return connection.query(Query.select(fields, table, condition));
    }

    public boolean update(ColumnValuePair[] fields, String table, Condition condition) {
        NokuResult res = connection.query(Query.update(fields, table, condition));
        return res.isSuccessful();
    }

    public boolean insert(ColumnValuePair[] fields, String table) {
        NokuResult res = connection.query(Query.insert(fields, table));
        return res.isSuccessful();
    }

    public boolean delete(String table, Condition condition) {
        NokuResult res = connection.query(Query.delete(table, condition));
        return res.isSuccessful();
    }

    public NokuResult custom(Query customQuery) {
        return connection.query(customQuery);
    }
}
