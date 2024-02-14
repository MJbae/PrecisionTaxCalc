package com.o3.mj.domain.tax;

import com.o3.mj.domain.customer.Customer;
import com.o3.mj.usecase.dto.ScrapingResponse;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TaxMapper {

    public Tax from(ScrapingResponse scrapingResponse, Customer customer) {
        BigDecimal calculatedTaxAmount = parseCalculatedTaxAmount(scrapingResponse);
        BigDecimal totalSalary = parseTotalSalary(scrapingResponse);
        Set<IncomeDeduction> incomeDeductions = parseIncomeDeductions(scrapingResponse);

        return new Tax(calculatedTaxAmount, totalSalary, incomeDeductions, customer);
    }

    private BigDecimal parseCalculatedTaxAmount(ScrapingResponse scrapingResponse) {
        String amountStr = scrapingResponse.getData().getJsonList().getCalculatedTaxAmount();
        return parseBigDecimal(amountStr);
    }

    private BigDecimal parseTotalSalary(ScrapingResponse scrapingResponse) {
        return scrapingResponse.getData().getJsonList().getSalaries().stream()
                .map(salary -> parseBigDecimal(salary.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<IncomeDeduction> parseIncomeDeductions(ScrapingResponse scrapingResponse) {
        return scrapingResponse.getData().getJsonList().getIncomeDeductions().stream()
                .map(deduction -> new IncomeDeduction(parseBigDecimal(deduction.getAmount()), DeductionType.fromDescription(deduction.getDeductionType())))
                .collect(Collectors.toSet());
    }

    private BigDecimal parseBigDecimal(String amountStr) {
        return new BigDecimal(Optional.ofNullable(amountStr).map(str -> str.replace(",", "")).orElse("0"));
    }
}
