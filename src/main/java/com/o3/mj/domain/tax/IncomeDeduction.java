package com.o3.mj.domain.tax;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
public class IncomeDeduction {

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "deduction_type")
    private DeductionType deductionType;

    public IncomeDeduction(BigDecimal amount, DeductionType deductionType){
        this.amount = amount;
        this.deductionType = deductionType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDeductionType(DeductionType deductionType) {
        this.deductionType = deductionType;
    }

    public DeductionType getDeductionType() {
        return this.deductionType;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
