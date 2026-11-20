package com.orderservice.dto;

public record CustomerResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String address
) {}
