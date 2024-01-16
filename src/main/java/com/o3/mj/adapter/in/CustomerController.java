package com.o3.mj.adapter.in;

import com.o3.mj.adapter.in.dto.SignUpRequest;
import com.o3.mj.usecase.CustomerService;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/szs/signup")
    public ResponseEntity<Long> signup(@RequestBody SignUpRequest request) {
        customerService.signup(request.toCommand());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
