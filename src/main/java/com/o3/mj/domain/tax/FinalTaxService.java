package com.o3.mj.domain.tax;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinalTaxService {
    private static final BigDecimal LABOR_INCOME_TAX_RATE = BigDecimal.valueOf(0.55);
    private static final BigDecimal STANDARD_DEDUCTION_LIMIT = BigDecimal.valueOf(130000);
    private final SpecialDeductionService specialDeductionService = new SpecialDeductionService();

    public BigDecimal calculate(Tax tax, BigDecimal retirementPensionDeduction) {
        BigDecimal laborIncomeDeduction = tax.getCalculatedTaxAmount().multiply(LABOR_INCOME_TAX_RATE);
        BigDecimal specialDeduction = specialDeductionService.calculate(tax);
        BigDecimal standardDeduction = specialDeduction.compareTo(STANDARD_DEDUCTION_LIMIT) < 0
                ? STANDARD_DEDUCTION_LIMIT : BigDecimal.ZERO;

        return tax.getCalculatedTaxAmount()
                .subtract(laborIncomeDeduction)
                .subtract(specialDeduction)
                .subtract(standardDeduction)
                .subtract(retirementPensionDeduction)
                .setScale(0, RoundingMode.DOWN)
                .max(BigDecimal.ZERO);
    }
}
