package com.sweden.association.membermanagement.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private long userAccountId;

    @Column(name = "username", nullable = false, length= 50, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 16)
    private String password;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    @Column(name = "verification_token_expiry_date")
    private Timestamp verificationTokenExpiryDate;

    @OneToOne(mappedBy = "userAccount", cascade = {CascadeType.ALL})
    private Member memberUserAccount;

    @OneToOne()
    @JoinColumn(name = "user_account_id")
    private AuthenticationSession userAccountAuthenticationSession;

    public AuthenticationSession getUserAccountAuthenticationSession() {
        return userAccountAuthenticationSession;
    }

    public void setUserAccountAuthenticationSession(AuthenticationSession userAccountAuthenticationSession) {
        this.userAccountAuthenticationSession = userAccountAuthenticationSession;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member getMemberUserAccount() {
        return memberUserAccount;
    }

    public void setMemberUserAccount(Member memberUserAccount) {
        this.memberUserAccount = memberUserAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Timestamp getVerificationTokenExpiryDate() {
        return verificationTokenExpiryDate;
    }

    public void setVerificationTokenExpiryDate(Timestamp verificationTokenExpiryDate) {
        this.verificationTokenExpiryDate = verificationTokenExpiryDate;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userAccountId=" + userAccountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", isActivated=" + isActivated +
                ", verificationToken='" + verificationToken + '\'' +
                ", verificationTokenExpiryDate=" + verificationTokenExpiryDate +
                ", memberUserAccount=" + memberUserAccount +
                ", userAccountAuthenticationSession=" + userAccountAuthenticationSession +
                '}';
    }
}
