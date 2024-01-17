package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
