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

import com.noku.base.CredentialProvider;

import java.util.Properties;

public final class PropertyCredentialProvider implements CredentialProvider {
    private Properties properties;
    public PropertyCredentialProvider(Properties properties){
        this.properties = properties;
    }

    public String getUsername() {
        return properties.getProperty("db.user", null);
    }

    public String getPassword() {
        return  properties.getProperty("db.pass", null);
    }

    public String getHost() {
        return  properties.getProperty("db.host", null);
    }

    public String getDatabase() {
        return  properties.getProperty("db.base", null);
    }
}
