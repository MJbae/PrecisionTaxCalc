package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerQuery {
    private String customerId;

    public CustomerQuery(String userId) {
        this.customerId = userId;
    }
}
