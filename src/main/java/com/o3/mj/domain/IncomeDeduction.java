package com.o3.mj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Embeddable
public class IncomeDeduction {

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "deduction_type")
    private DeductionType deductionType;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDeductionType(DeductionType deductionType) {
        this.deductionType = deductionType;
    }
}
