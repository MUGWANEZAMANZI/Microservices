package com.deliveryservice.dto;

public record OrderCancelledEvent(
        String orderId,
        Long customerId,
        Long restaurantId,
        String status) {
}
