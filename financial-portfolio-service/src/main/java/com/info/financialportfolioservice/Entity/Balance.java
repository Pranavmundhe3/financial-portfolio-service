package com.info.financialportfolioservice.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@ToString
public class Balance implements Serializable {

    private static final long serialVersionUID = 2L;

    private String month;
    private BigDecimal monthlyAmount;
    private BigDecimal cumulativeAmount;

    // Amount is defined using BigDecimal type so that it can hold long range of literals
}
