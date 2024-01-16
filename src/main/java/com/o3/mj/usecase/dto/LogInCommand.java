package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInCommand {
    private String customerId;
    private String password;

    public LogInCommand(String customerId, String password) {
        this.customerId = customerId;
        this.password = password;
    }
}
