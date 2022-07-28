package com.sweden.association.membermanagement.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long memberId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "mobile_number", nullable = false, unique = true, length = 12)
    private String mobileNumber;

    @OneToMany(mappedBy = "payer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Payment> payments;

    @OneToOne(mappedBy = "memberUserAccount")
    private UserAccount userAccount;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Member))
            return false;
        Member member = (Member) o;
        return memberId == member.memberId && Objects.equals(name, member.name)
                && Objects.equals(mobileNumber, member.mobileNumber)
                && Objects.equals(payments, member.payments) && Objects.equals(userAccount, member.userAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name, mobileNumber, payments, userAccount);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", payments=" + payments +
                ", userAccount=" + userAccount +
                '}';
    }
}