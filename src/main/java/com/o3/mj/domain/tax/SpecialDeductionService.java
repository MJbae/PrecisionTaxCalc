package com.o3.mj.domain.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class SpecialDeductionService {
    private static final BigDecimal INSURANCE_RATE = BigDecimal.valueOf(0.12);
    private static final BigDecimal EDUCATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal DONATION_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_RATE = BigDecimal.valueOf(0.15);
    private static final BigDecimal MEDICAL_THRESHOLD_RATE = BigDecimal.valueOf(0.03);

    public BigDecimal calculate(Tax tax) {
        BigDecimal insuranceAmount = tax.getDeductionAmount(DeductionType.INSURANCE);
        BigDecimal medicalExpenses = tax.getDeductionAmount(DeductionType.MEDICAL);
        BigDecimal educationExpenses = tax.getDeductionAmount(DeductionType.EDUCATION);
        BigDecimal donationAmount = tax.getDeductionAmount(DeductionType.DONATION);

        BigDecimal educationDeduction = educationExpenses.multiply(EDUCATION_RATE);
        BigDecimal donationDeduction = donationAmount.multiply(DONATION_RATE);
        BigDecimal insuranceDeduction = insuranceAmount.multiply(INSURANCE_RATE);
        BigDecimal medicalDeduction = medicalExpenses
                .subtract(tax.getTotalSalary().multiply(MEDICAL_THRESHOLD_RATE))
                .multiply(MEDICAL_RATE)
                .max(BigDecimal.ZERO);

        return medicalDeduction
                .add(insuranceDeduction)
                .add(donationDeduction)
                .add(educationDeduction)
                .setScale(0, RoundingMode.DOWN);
    }
}