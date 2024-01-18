package com.o3.mj.usecase.dto;


import com.o3.mj.domain.Customer;
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

    public ScrapingRequest from(Customer customer) {
        return new ScrapingRequest(customer.getName(), customer.getOriginResidentId());
    }
}
