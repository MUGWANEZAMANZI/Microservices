package com.orderservice.dto;

public record OrderCancelEvent (
        String orderId,
       Long customerId,
       Long restaurantId,
        String status){
}
