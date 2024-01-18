package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.adapter.out.TaxRepository;
import com.o3.mj.domain.*;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.dto.RefundResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import com.o3.mj.usecase.exception.NotRegisteredTaxException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Optional;

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
    public RefundResponse refund(CustomerQuery query) throws HttpClientErrorException {
        Optional<Customer> customer = repository.findById(new CustomerId(query.getCustomerId()));
        if (customer.isEmpty()) {
            throw new NotRegisteredCustomerException(query.getCustomerId());
        }

        Optional<Tax> tax = taxRepository.findByCustomer(customer.get());
        if (tax.isEmpty()) {
            throw new NotRegisteredTaxException(query.getCustomerId());
        }

        BigDecimal finalTaxAmount =  calculator.calculateFinalTaxAmount(tax.get());
        BigDecimal retirementPensionDeduction = calculator.calculateRetirementPensionDeduction(tax.get());

        return new RefundResponse(customer.get().getName(), finalTaxAmount.toString(), retirementPensionDeduction.toString());
    }

}
