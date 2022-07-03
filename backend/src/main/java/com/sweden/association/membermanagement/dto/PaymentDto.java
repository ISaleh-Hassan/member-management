package com.sweden.association.membermanagement.dto;

import java.math.BigDecimal;

public class PaymentDto {
    private final String memberName;
    private final String mobileNumber;
    private final BigDecimal amount;
    private final String transactionDate;

    public PaymentDto(String memberName, String mobileNumber, BigDecimal amount, String transactionDate) {
        this.memberName = memberName;
        this.mobileNumber = mobileNumber;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
}
