package com.sweden.association.membermanagement.utility;

public class JwtResponse {
    private String jwtToken;
    private Boolean mobileNumberExists;
    private Boolean usernameExists;
    private Boolean emailExists;
    private Boolean userRegisteredSuccess;
    private Boolean isAdmin;
    private Boolean isActivated;
    private Boolean invalidCredentials;

    public Boolean getInvalidCredentials() {
        return invalidCredentials;
    }

    public void setInvalidCredentials(Boolean invalidCredentials) {
        this.invalidCredentials = invalidCredentials;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getUserRegisteredSuccess() {
        return userRegisteredSuccess;
    }

    public void setUserRegisteredSuccess(Boolean userRegisteredSuccess) {
        this.userRegisteredSuccess = userRegisteredSuccess;
    }

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