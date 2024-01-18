package com.o3.mj.domain.tax;

import java.math.BigDecimal;


public class SpecialDeductionService {
    private static final BigDecimal INSURANCE_RATE = BigDecimal.valueOf(0.12);
    private static final BigDecimal EDUCATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal DONATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_THRESHOLD_RATE = BigDecimal.valueOf(0.03);

    public BigDecimal calculate(Tax tax) {
        BigDecimal insuranceAmount = TaxUtils.getDeductionAmount(tax, DeductionType.INSURANCE);
        BigDecimal medicalExpenses = TaxUtils.getDeductionAmount(tax, DeductionType.MEDICAL);
        BigDecimal educationExpenses = TaxUtils.getDeductionAmount(tax, DeductionType.EDUCATION);
        BigDecimal donationAmount = TaxUtils.getDeductionAmount(tax, DeductionType.DONATION);

        BigDecimal insuranceDeduction = insuranceAmount.multiply(INSURANCE_RATE);
        BigDecimal medicalDeduction = medicalExpenses.subtract(tax.getTotalSalary().multiply(MEDICAL_THRESHOLD_RATE))
                .multiply(MEDICAL_RATE)
                .max(BigDecimal.ZERO);
        BigDecimal educationDeduction = educationExpenses.multiply(EDUCATION_RATE);
        BigDecimal donationDeduction = donationAmount.multiply(DONATION_RATE);

        return insuranceDeduction.add(medicalDeduction).add(educationDeduction).add(donationDeduction);
    }
}