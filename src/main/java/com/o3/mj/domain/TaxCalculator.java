package com.o3.mj.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {

    private static final BigDecimal STANDARD_DEDUCTION_LIMIT = BigDecimal.valueOf(130000);
    private static final BigDecimal LABOR_INCOME_TAX_RATE = BigDecimal.valueOf(0.55);
    private static final BigDecimal RETIREMENT_PENSION_TAX_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal INSURANCE_RATE = BigDecimal.valueOf(0.12);
    private static final BigDecimal EDUCATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal DONATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_THRESHOLD_RATE = BigDecimal.valueOf(0.03);

    public BigDecimal calculateFinalTaxAmount(Tax tax) {
        BigDecimal laborIncomeDeduction = tax.getCalculatedTaxAmount().multiply(LABOR_INCOME_TAX_RATE);
        BigDecimal retirementPensionDeduction = calculateRetirementPensionDeduction(tax);
        BigDecimal specialDeduction = calculateSpecialDeduction(tax);
        BigDecimal standardDeduction = calculateStandardDeduction(specialDeduction);

        return tax.getCalculatedTaxAmount()
                .subtract(laborIncomeDeduction)
                .subtract(specialDeduction)
                .subtract(standardDeduction)
                .subtract(retirementPensionDeduction)
                .setScale(0, RoundingMode.DOWN)
                .max(BigDecimal.ZERO);
    }

    public BigDecimal calculateRetirementPensionDeduction(Tax tax) {
        BigDecimal retirementPensionAmount = getDeductionAmount(tax, DeductionType.RETIREMENT_PENSION);
        return retirementPensionAmount.multiply(RETIREMENT_PENSION_TAX_RATE).setScale(0, RoundingMode.DOWN);
    }

    private BigDecimal calculateSpecialDeduction(Tax tax) {
        BigDecimal insuranceAmount = getDeductionAmount(tax, DeductionType.INSURANCE);
        BigDecimal medicalExpenses = getDeductionAmount(tax, DeductionType.MEDICAL);
        BigDecimal educationExpenses = getDeductionAmount(tax, DeductionType.EDUCATION);
        BigDecimal donationAmount = getDeductionAmount(tax, DeductionType.DONATION);

        BigDecimal insuranceDeduction = insuranceAmount.multiply(INSURANCE_RATE);
        BigDecimal medicalDeduction = calculateMedicalDeduction(medicalExpenses, tax.getTotalSalary());
        BigDecimal educationDeduction = educationExpenses.multiply(EDUCATION_RATE);
        BigDecimal donationDeduction = donationAmount.multiply(DONATION_RATE);

        return insuranceDeduction.add(medicalDeduction).add(educationDeduction).add(donationDeduction);
    }

    private BigDecimal calculateMedicalDeduction(BigDecimal medicalExpenses, BigDecimal totalSalary) {
        BigDecimal deduction = medicalExpenses.subtract(totalSalary.multiply(MEDICAL_THRESHOLD_RATE)).multiply(MEDICAL_RATE);
        return deduction.compareTo(BigDecimal.ZERO) > 0 ? deduction : BigDecimal.ZERO;
    }

    private BigDecimal calculateStandardDeduction(BigDecimal specialDeduction) {
        return specialDeduction.compareTo(STANDARD_DEDUCTION_LIMIT) < 0 ? STANDARD_DEDUCTION_LIMIT : BigDecimal.ZERO;
    }

    private BigDecimal getDeductionAmount(Tax tax, DeductionType type) {
        return tax.getIncomeDeductions().stream()
                .filter(d -> d.getDeductionType() == type)
                .map(IncomeDeduction::getAmount)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}
