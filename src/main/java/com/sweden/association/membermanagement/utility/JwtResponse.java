package com.sweden.association.membermanagement.utility;
public class JwtResponse{
private String jwtToken;

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