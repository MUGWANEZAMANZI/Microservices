package com.customerservice.dto;

public record UserRegistrationResponse(Long id, String fullName, String email, String token) {}
