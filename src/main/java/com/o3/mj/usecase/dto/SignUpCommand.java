package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpCommand {
    private String customerId;
    private String password;
    private String name;
    private String residentId;

    public SignUpCommand(String userId, String password, String name, String regNo) {
        this.customerId = userId;
        this.password = password;
        this.name = name;
        this.residentId = regNo;
    }
}
