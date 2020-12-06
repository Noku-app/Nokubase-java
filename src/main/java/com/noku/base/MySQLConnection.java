package com.noku.base;

import java.util.Properties;

public interface MySQLConnection<T extends ResultProvider> {
    /**
     * Set credentials and database to use with this connection and connect to it.
     * @param provider parameter to obtain credentials from.
     */
    public void connect(CredentialProvider provider);
    public T query(Query q);
    public String getError();
}
