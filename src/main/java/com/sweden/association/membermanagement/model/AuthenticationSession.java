package com.sweden.association.membermanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "authentication_session")
public class AuthenticationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentication_session_id")
    private long authentication_session_id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "token_expiry_date", nullable = false)
    private Date tokenExpiryTimestamp;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiryTimestamp() {
        return tokenExpiryTimestamp;
    }

    public void setTokenExpiryTimestamp(Date tokenExpiryTimestamp) {
        this.tokenExpiryTimestamp = tokenExpiryTimestamp;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public long getAuthentication_session_id() {
        return authentication_session_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationSession)) return false;
        AuthenticationSession that = (AuthenticationSession) o;
        return authentication_session_id == that.authentication_session_id && Objects.equals(token, that.token) && Objects.equals(tokenExpiryTimestamp, that.tokenExpiryTimestamp) && Objects.equals(userAccount, that.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authentication_session_id, token, tokenExpiryTimestamp, userAccount);
    }
}
