package com.orderservice.dto;


/**
 * This record represents the event of an order being created.
 * @param orderId
 * @param customerId
 * @param restaurantId
 * @param status
 */
public record OrderCreatedEvent(
    String orderId,
    Long customerId,
    Long restaurantId,
    String status
) {
}
