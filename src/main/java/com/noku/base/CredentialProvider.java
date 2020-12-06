package com.noku.base;

public interface CredentialProvider {
    public String getUsername();
    public String getPassword();
    public String getHost();
    public String getDatabase();
}
