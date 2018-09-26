package com.n26.entity;

import java.math.BigDecimal;

/*
 * Entity class which holds Transaction.
 * @author ttdduman
 */

public class Transaction {

    private BigDecimal amount;
    private Long milliseconds;

    public Transaction(BigDecimal amount, Long milliseconds) {
        this.amount = amount;
        this.milliseconds = milliseconds;
    }

    public Transaction() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
