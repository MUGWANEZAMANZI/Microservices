package com.deliveryservice.client.fallback;

import com.deliveryservice.client.DeliveryClient;
import com.deliveryservice.dto.OrderResponse;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderClientFallback implements DeliveryClient {
    @Override
    public OrderResponse getOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }
}
