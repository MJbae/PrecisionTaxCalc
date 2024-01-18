package com.o3.mj.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private final String status = "fail";
    private String message;

    public ErrorResponse(String message) {;
        this.message = message;
    }
}
