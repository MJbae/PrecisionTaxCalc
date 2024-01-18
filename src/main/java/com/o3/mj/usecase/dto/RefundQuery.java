package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundQuery {
    private String customerId;

    public RefundQuery(String userId) {
        this.customerId = userId;
    }
}
