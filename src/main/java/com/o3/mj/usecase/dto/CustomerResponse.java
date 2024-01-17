package com.o3.mj.usecase.dto;

import com.o3.mj.domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CustomerResponse {
    private String customerId;
    private String name;

    public CustomerResponse(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public CustomerResponse from(Customer customer){
        return new CustomerResponse(customer.getId().toString(), customer.getName());
    }
}
