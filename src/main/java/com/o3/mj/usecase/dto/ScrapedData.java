package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ScrapedData {
    private String customerId;

    public ScrapedData(String customerId) {
        this.customerId = customerId;
    }
}
