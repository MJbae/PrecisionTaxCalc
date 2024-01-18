package com.o3.mj.usecase.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapingRequest {
    private String name;
    private String regNo;

    public ScrapingRequest(String name, String regNo) {
        this.name = name;
        this.regNo = regNo;
    }
}
