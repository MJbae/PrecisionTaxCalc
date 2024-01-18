package com.o3.mj.usecase;

import com.o3.mj.domain.customer.*;
import com.o3.mj.repository.CustomerRepository;
import com.o3.mj.usecase.dto.*;
import com.o3.mj.usecase.exception.NotAllowedRegisterException;
import com.o3.mj.usecase.exception.RedundantCustomerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SignUpCustomerService {
    private final Encryptor encryptor = new Encryptor();
    private final CustomerFactory factory = new CustomerFactory();
    private final SignUpWhiteList whiteList = new SignUpWhiteList();
    private final CustomerRepository repository;

    public SignUpCustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TokenResponse signup(SignUpCommand command) {
        if (!whiteList.isAllowedToSignup(new ResidentId(command.getResidentId()))) {
            throw new NotAllowedRegisterException(command.getCustomerId());
        }

        Optional<Customer> redundantCustomer = repository.findById(new CustomerId(command.getCustomerId()));
        if (redundantCustomer.isPresent()) {
            throw new RedundantCustomerException(redundantCustomer.get());
        }

        Customer customer = factory.createFrom(command);
        repository.save(customer);

        return new TokenResponse(encryptor.encrypt(customer));
    }
}
