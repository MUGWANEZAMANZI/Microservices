package com.deliveryservice.dto;

public record OrderResponse(
        Long id,
        Long customerId,
        Long restaurantId,
        Double totalAmount,
        String status
) {
}
