package com.orderservice.dto;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String cuisineType
) {}
