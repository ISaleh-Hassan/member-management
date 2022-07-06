package com.sweden.association.membermanagement.utility;

public class JwtResponse {
    private String jwtToken;
    private Boolean mobileNumberExists;
    private Boolean usernameExists;
    private Boolean emailExists;

    public Boolean getMobileNumberExists() {
        return mobileNumberExists;
    }

    public void setMobileNumberExists(Boolean mobileNumberExists) {
        this.mobileNumberExists = mobileNumberExists;
    }

    public Boolean getEmailExist() {
        return emailExists;
    }

    public void setEmailExists(Boolean emailExists) {
        this.emailExists = emailExists;
    }

    public Boolean getUsernameExists() {
        return usernameExists;
    }

    public void setUsernameExists(Boolean usernameExists) {
        this.usernameExists = usernameExists;
    }

    public JwtResponse() {
        super();
    }

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}