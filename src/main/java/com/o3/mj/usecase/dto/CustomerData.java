package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerData {
    private String customerId;

    public CustomerData(String customerId) {
        this.customerId = customerId;
    }
}
