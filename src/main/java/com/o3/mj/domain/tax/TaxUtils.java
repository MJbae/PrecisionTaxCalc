package com.o3.mj.domain.tax;

import java.math.BigDecimal;

public class TaxUtils {
    public static BigDecimal getDeductionAmount(Tax tax, DeductionType type) {
        return tax.getIncomeDeductions().stream()
                .filter(d -> d.getDeductionType() == type)
                .map(IncomeDeduction::getAmount)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}