package com.o3.mj.adapter.in;

import com.o3.mj.adapter.in.dto.LogInRequest;
import com.o3.mj.adapter.in.dto.SignUpRequest;
import com.o3.mj.usecase.CustomerService;
import com.o3.mj.usecase.ScrapCustomerService;
import com.o3.mj.usecase.dto.CustomerData;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.dto.CustomerResponse;
import com.o3.mj.usecase.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "고객 관리 API")
public class CustomerController {
    private final CustomerService customerService;
    private final ScrapCustomerService scrapService;

    public CustomerController(CustomerService customerService, ScrapCustomerService scrapService) {
        this.customerService = customerService;
        this.scrapService = scrapService;
    }

    @PostMapping("/szs/signup")
    @Operation(summary = "회원가입 API")
    public TokenResponse signup(@RequestBody SignUpRequest request) {
        return customerService.signup(request.toCommand());
    }

    @PostMapping("/szs/login")
    @Operation(summary = "로그인 API")
    public TokenResponse login(@RequestBody LogInRequest request) {
        return customerService.login(request.toCommand());
    }

    @GetMapping("/szs/me")
    @Secured("ROLE_USER")
    @Operation(summary = "본인 정보 조회 API", security = { @SecurityRequirement(name = "BearerToken") })
    public CustomerResponse searchMe(@AuthenticationPrincipal CustomerData customer) {
        return customerService.searchMe(new CustomerQuery(customer.getCustomerId()));
    }

    @PostMapping("/szs/scrap")
    @Secured("ROLE_USER")
    @Operation(summary = "본인 정보 스크랩 API", security = { @SecurityRequirement(name = "BearerToken") })
    public void scrap(@AuthenticationPrincipal CustomerData customer) {
        scrapService.scrap(new CustomerQuery(customer.getCustomerId()));
    }
}
