package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.adapter.out.TaxRepository;
import com.o3.mj.domain.*;
import com.o3.mj.usecase.dto.RefundQuery;
import com.o3.mj.usecase.dto.RefundResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import com.o3.mj.usecase.exception.NotRegisteredTaxException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

@Service
public class RefundTaxService {
    private final CustomerRepository repository;
    private final TaxRepository taxRepository;
    private final TaxCalculator calculator = new TaxCalculator();

    public RefundTaxService(CustomerRepository repository, TaxRepository taxRepository) {
        this.repository = repository;
        this.taxRepository = taxRepository;
    }

    @Transactional
    public RefundResponse refund(RefundQuery query) throws HttpClientErrorException {
        Customer customer = repository.findById(new CustomerId(query.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(query.getCustomerId()));

        Tax tax = taxRepository.findByCustomer(customer)
                .orElseThrow(() -> new NotRegisteredTaxException(query.getCustomerId()));

        BigDecimal retirementPensionDeduction = calculator.calculateRetirementPensionDeduction(tax);
        BigDecimal finalTaxAmount = calculator.calculateFinalTaxAmount(tax, retirementPensionDeduction);

        return new RefundResponse(customer.getName(), finalTaxAmount.toString(), retirementPensionDeduction.toString());
    }
}
