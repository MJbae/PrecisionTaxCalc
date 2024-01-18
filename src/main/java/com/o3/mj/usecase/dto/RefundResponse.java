package com.o3.mj.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RefundResponse {
    private String name;
    private String finalTaxAmount;
    private String retirementPensionDeduction;

    public RefundResponse(String name, String finalTaxAmount, String retirementPensionDeduction) {
        this.name = name;
        this.finalTaxAmount = finalTaxAmount;
        this.retirementPensionDeduction = retirementPensionDeduction;
    }
}
