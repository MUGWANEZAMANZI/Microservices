package com.orderservice.service;

import com.orderservice.client.RestaurantClient;
import com.orderservice.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    @Override
    public RestaurantResponse getRestaurant(Long id) {
        return null;
    }
}
