package com.deliveryservice.dto;

public record DeliveryStatusUpdatedEvent(
        Long deliveryId,
        Long orderId,
        String driverName,
        String status) {
}
