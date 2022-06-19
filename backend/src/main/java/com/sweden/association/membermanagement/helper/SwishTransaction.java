package com.sweden.association.membermanagement.helper;

import java.math.BigDecimal;
import java.util.Objects;

public class SwishTransaction {
    private String date;
    private BigDecimal amount;

    public SwishTransaction(String date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwishTransaction)) return false;
        SwishTransaction that = (SwishTransaction) o;
        return Objects.equals(date, that.date) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount);
    }
}
