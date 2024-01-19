package com.o3.mj.domain.tax;


import com.o3.mj.domain.customer.Customer;
import com.o3.mj.usecase.dto.ScrapingResponse;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

public class TaxMapper {

    public Tax from(ScrapingResponse scrapingResponse, Customer customer) {
        BigDecimal calculatedTaxAmount = new BigDecimal(scrapingResponse.getData().getJsonList().getCalculatedTaxAmount().replace(",", ""));

        BigDecimal totalSalary = scrapingResponse.getData().getJsonList().getSalaries().stream()
                .map(salary -> new BigDecimal(salary.getAmount().replace(",", "")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Set<IncomeDeduction> incomeDeductions = scrapingResponse.getData().getJsonList().getIncomeDeductions().stream()
                .map(deduction -> new IncomeDeduction(parseBigDecimal(deduction.getAmount()), DeductionType.fromDescription(deduction.getDeductionType())))
                .collect(Collectors.toSet());

        return new Tax(calculatedTaxAmount, totalSalary, incomeDeductions, customer);
    }

    private BigDecimal parseBigDecimal(String amountStr) {
        return Optional.ofNullable(amountStr)
                .map(str -> new BigDecimal(str.replace(",", "")))
                .orElse(BigDecimal.ZERO);
    }
}
