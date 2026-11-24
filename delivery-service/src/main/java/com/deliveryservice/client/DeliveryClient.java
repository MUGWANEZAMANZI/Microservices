package com.deliveryservice.client;


import com.deliveryservice.client.fallback.OrderClientFallback;
import com.deliveryservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service", fallback = OrderClientFallback.class)
public interface DeliveryClient {

    @GetMapping("/api/orders/{id}")
    OrderResponse getOrderById(@PathVariable Long id);

    @GetMapping("api/orders")
    List<OrderResponse> getAllOrders();
}
