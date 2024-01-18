package com.o3.mj.adapter.in;

import com.o3.mj.adapter.in.dto.LogInRequest;
import com.o3.mj.adapter.in.dto.SignUpRequest;
import com.o3.mj.usecase.*;
import com.o3.mj.usecase.dto.*;
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
public class AuthenticationController {
    private final SearchCustomerService searchService;
    private final SignUpCustomerService signupService;
    private final LoginCustomerService loginService;

    public AuthenticationController(SearchCustomerService searchService, SignUpCustomerService signupService, LoginCustomerService loginService) {
        this.searchService = searchService;
        this.signupService = signupService;
        this.loginService = loginService;
    }

    @PostMapping("/szs/signup")
    @Operation(summary = "회원가입 API")
    public TokenResponse signup(@RequestBody SignUpRequest request) {
        return signupService.signup(request.toCommand());
    }

    @PostMapping("/szs/login")
    @Operation(summary = "로그인 API")
    public TokenResponse login(@RequestBody LogInRequest request) {
        return loginService.login(request.toCommand());
    }

    @GetMapping("/szs/me")
    @Secured("ROLE_USER")
    @Operation(summary = "본인 정보 조회 API", security = {@SecurityRequirement(name = "BearerToken")})
    public CustomerResponse searchMe(@AuthenticationPrincipal CustomerData customer) {
        return searchService.searchMe(new CustomerQuery(customer.getCustomerId()));
    }
}
