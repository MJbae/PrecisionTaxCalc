package com.o3.mj.domain;

import com.o3.mj.usecase.dto.SignUpCommand;

public class CustomerFactory {

    public Customer createFrom(SignUpCommand command) {
        return new Customer(
                new CustomerId(command.getCustomerId()),
                command.getName(),
                new ResidentId(command.getResidentId()),
                new Password(command.getPassword())
        );
    }
}
