package com.n26.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.util.Date;

/*
 * Transaction Dto class which holds transaction requests.
 * @author ttdduman
 */
public class TransactionDto {

    @NotNull(message = "Timestamp can not be null or empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date timestamp;
    
    @NotNull(message = "Amount can not be null or empty")
    @DecimalMin(value = "0.00", message = "Amount can not be lower than 0.00")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "amount='" + amount + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
