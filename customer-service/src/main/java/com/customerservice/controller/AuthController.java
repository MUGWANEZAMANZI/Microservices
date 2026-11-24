package com.customerservice.controller;

import com.customerservice.dto.LoginRequest;
import com.customerservice.dto.LoginResponse;
import com.customerservice.dto.UserRegistrationRequest;
import com.customerservice.dto.UserRegistrationResponse;
import com.customerservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(customerService.login(request));
    }
}
