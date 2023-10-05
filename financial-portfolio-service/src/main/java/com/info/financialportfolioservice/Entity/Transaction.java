package com.info.financialportfolioservice.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private LocalDate date;
    private Type type;

    public enum Type {
        CREDIT,
        DEBIT
    }

    // Amount is defined using BigDecimal type so that it can hold long range of literals
}
