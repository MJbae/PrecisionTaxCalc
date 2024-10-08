package com.o3.mj.usecase;

import com.o3.mj.repository.CustomerRepository;
import com.o3.mj.domain.customer.Customer;
import com.o3.mj.domain.customer.CustomerId;
import com.o3.mj.usecase.dto.CustomerData;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.dto.CustomerResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SearchCustomerService {
    private final CustomerRepository repository;

    public SearchCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public CustomerData search(CustomerQuery query) {
        Customer customer = repository.findById(new CustomerId(query.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(query.getCustomerId()));

        return new CustomerData(customer.getId().toString());
    }

    @Transactional(readOnly = true)
    public CustomerResponse searchMe(CustomerQuery query) {
        Customer customer = repository.findById(new CustomerId(query.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(query.getCustomerId()));

        return new CustomerResponse(customer);
    }
}
