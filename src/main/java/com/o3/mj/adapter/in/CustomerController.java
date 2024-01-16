package com.o3.mj.adapter.in;

import com.o3.mj.adapter.in.dto.SignUpRequest;
import com.o3.mj.usecase.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "고객 관리 API")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/szs/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<Long> signup(@RequestBody SignUpRequest request) {
        customerService.signup(request.toCommand());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
