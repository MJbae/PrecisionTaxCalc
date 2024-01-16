package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerFactory;
import com.o3.mj.usecase.dto.SignUpCommand;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerFactory factory = new CustomerFactory();
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void signup(SignUpCommand command) {
        Customer customer = factory.createFrom(command);
        repository.save(customer);
    }
}
