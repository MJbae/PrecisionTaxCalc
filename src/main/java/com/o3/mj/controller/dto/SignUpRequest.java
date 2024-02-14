package com.o3.mj.controller.dto;

import com.o3.mj.usecase.dto.SignUpCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpRequest {
    private String userId;
    private String password;
    private String name;
    private String regNo;

    public SignUpCommand toCommand(){
        return new SignUpCommand(userId, password, name, regNo);
    }
}
