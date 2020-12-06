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
