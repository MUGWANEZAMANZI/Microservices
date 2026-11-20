package com.deliveryservice.dto;

public record OrderCreatedEvent(
    String orderId,
    Long customerId,
    Long restaurantId,
    String status
) {
}
