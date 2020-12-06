package com.noku.base.javase;

import com.noku.base.CredentialProvider;

import java.util.Properties;

public final class PropertyCredentialProvider implements CredentialProvider {
    private Properties properties;
    public PropertyCredentialProvider(Properties properties){
        this.properties = properties;
    }

    public String getUsername() {
        return properties.getProperty("user", null);
    }

    public String getPassword() {
        return  properties.getProperty("pass", null);
    }

    public String getHost() {
        return  properties.getProperty("host", null);
    }

    public String getDatabase() {
        return  properties.getProperty("base", null);
    }
}
