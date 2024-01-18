package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapCommand {
    private String customerId;

    public ScrapCommand(String userId) {
        this.customerId = userId;
    }
}
