package com.customerservice.dto;

public record LoginResponse(String token, String email, String fullName) {}
