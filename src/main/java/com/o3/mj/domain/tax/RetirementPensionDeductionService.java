package com.o3.mj.domain.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RetirementPensionDeductionService {
    private static final BigDecimal RETIREMENT_PENSION_TAX_RATE = BigDecimal.valueOf(0.15);

    public BigDecimal calculate(Tax tax) {
        BigDecimal retirementPensionAmount = TaxUtils.getDeductionAmount(tax, DeductionType.RETIREMENT_PENSION);
        return retirementPensionAmount.multiply(RETIREMENT_PENSION_TAX_RATE).setScale(0, RoundingMode.DOWN);
    }
}