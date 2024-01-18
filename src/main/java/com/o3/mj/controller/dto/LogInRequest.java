package com.o3.mj.controller.dto;

import com.o3.mj.usecase.dto.LogInCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LogInRequest {
    private String userId;
    private String password;

    public LogInCommand toCommand(){
        return new LogInCommand(userId, password);
    }
}
