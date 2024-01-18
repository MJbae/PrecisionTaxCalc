package com.o3.mj.usecase.dto;

import com.o3.mj.domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CustomerResponse {
    private String customerId;
    private String name;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getId().toString();
        this.name = customer.getName();
    }
}
