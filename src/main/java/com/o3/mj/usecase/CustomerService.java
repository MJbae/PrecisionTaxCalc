package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerFactory;
import com.o3.mj.domain.CustomerId;
import com.o3.mj.domain.Encryptor;
import com.o3.mj.usecase.dto.LogInCommand;
import com.o3.mj.usecase.dto.SignUpCommand;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import com.o3.mj.usecase.exception.RedundantCustomerException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final Encryptor encryptor = new Encryptor();
    private final CustomerFactory factory = new CustomerFactory();
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void signup(SignUpCommand command) {
        Optional<Customer> redundantCustomer = repository.findById(new CustomerId(command.getCustomerId()));
        if (redundantCustomer.isPresent()) {
            throw new RedundantCustomerException(redundantCustomer.get());
        }

        Customer customer = factory.createFrom(command);
        repository.save(customer);
    }

    public String login(LogInCommand command) {
        Optional<Customer> customer = repository.findById(new CustomerId(command.getCustomerId()));
        if (customer.isEmpty()) {
            throw new NotRegisteredCustomerException(command.getCustomerId());
        }

        return encryptor.encrypt(command.getCustomerId());
    }
}
