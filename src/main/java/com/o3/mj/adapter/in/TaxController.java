package com.o3.mj.adapter.in;

import com.o3.mj.usecase.RefundTaxService;
import com.o3.mj.usecase.ScrapTaxService;
import com.o3.mj.usecase.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "세액 계산 API")
public class TaxController {
    private final ScrapTaxService scrapService;
    private final RefundTaxService refundService;

    public TaxController(ScrapTaxService scrapService, RefundTaxService refundService) {
        this.scrapService = scrapService;
        this.refundService = refundService;
    }


    @PostMapping("/szs/scrap")
    @Secured("ROLE_USER")
    @Operation(summary = "세금 정보 스크랩 API", security = {@SecurityRequirement(name = "BearerToken")})
    public void scrap(@AuthenticationPrincipal CustomerData customer) {
        scrapService.scrap(new ScrapCommand(customer.getCustomerId()));
    }

    @GetMapping("/szs/refund")
    @Secured("ROLE_USER")
    @Operation(summary = "환금액 계산 API", security = {@SecurityRequirement(name = "BearerToken")})
    public RefundResponse refund(@AuthenticationPrincipal CustomerData customer) {
        return refundService.refund(new RefundQuery(customer.getCustomerId()));
    }
}
