package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerId;
import com.o3.mj.usecase.dto.CustomerData;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchCustomerService {
    private final CustomerRepository repository;

    public SearchCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerData search(CustomerQuery query) {
        Optional<Customer> customer = repository.findById(new CustomerId(query.getCustomerId()));
        if (customer.isEmpty()) {
            throw new NotRegisteredCustomerException(query.getCustomerId());
        }

        return new CustomerData(query.getCustomerId());
    }
}
