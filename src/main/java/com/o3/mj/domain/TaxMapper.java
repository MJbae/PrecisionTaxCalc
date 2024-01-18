package com.o3.mj.domain;


import com.o3.mj.usecase.dto.ScrapedResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

public class TaxMapper {

    public Tax from(ScrapedResponse scrapedResponse, Customer customer) {
        Tax tax = new Tax();

        BigDecimal calculatedTaxAmount = new BigDecimal(scrapedResponse.getData().getJsonList().getCalculatedTaxAmount().replace(",", ""));
        tax.setCalculatedTaxAmount(calculatedTaxAmount);

        BigDecimal totalSalary = scrapedResponse.getData().getJsonList().getSalaries().stream()
                .map(salary -> new BigDecimal(salary.getAmount().replace(",", "")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        tax.setTotalSalary(totalSalary);

        Set<IncomeDeduction> incomeDeductions = scrapedResponse.getData().getJsonList().getIncomeDeductions().stream()
                .map(deduction -> new IncomeDeduction(parseBigDecimal(deduction.getAmount()), DeductionType.fromDescription(deduction.getDeductionType())))
                .collect(Collectors.toSet());
        tax.setIncomeDeductions(incomeDeductions);

        tax.setCustomer(customer);

        return tax;
    }

    private BigDecimal parseBigDecimal(String amountStr) {
        return Optional.ofNullable(amountStr)
                .map(str -> new BigDecimal(str.replace(",", "")))
                .orElse(BigDecimal.ZERO);
    }
}
