package com.o3.mj.usecase;

import com.o3.mj.repository.CustomerRepository;
import com.o3.mj.domain.customer.Customer;
import com.o3.mj.domain.customer.CustomerId;
import com.o3.mj.domain.customer.Encryptor;
import com.o3.mj.usecase.dto.LogInCommand;
import com.o3.mj.usecase.dto.TokenResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LoginCustomerService {
    private final Encryptor encryptor = new Encryptor();
    private final CustomerRepository repository;

    public LoginCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TokenResponse login(LogInCommand command) {
        Customer customer = repository.findById(new CustomerId(command.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(command.getCustomerId()));

        if (!customer.hasSamePassword(command.getPassword())){
            throw new BadCredentialsException("Not Matched Password, userId: " + command.getCustomerId());
        }

        return new TokenResponse(encryptor.encrypt(customer));
    }
}