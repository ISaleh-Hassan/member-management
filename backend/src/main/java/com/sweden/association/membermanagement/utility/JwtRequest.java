package com.sweden.association.membermanagement.utility;

public class JwtRequest {
    public String username;
    public String password;

    public JwtRequest() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}