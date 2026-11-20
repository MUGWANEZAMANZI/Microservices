package com.orderservice.service;

import com.orderservice.client.CustomerClient;
import com.orderservice.client.RestaurantClient;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final RestaurantClient restaurantClient;

    public OrderService(OrderRepository repository,
                        CustomerClient customerClient,
                        RestaurantClient restaurantClient) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.restaurantClient = restaurantClient;
    }

    public Order placeOrder(Order order) {
        customerClient.getCustomer(order.getCustomerId());
        restaurantClient.getRestaurant(order.getRestaurantId());
        order.setStatus("PENDING");
        return repository.save(order);
    }

    public Order create(Order order) {
        order.setStatus("PENDING");
        return repository.save(order);
    }

    public List<Order> findAll() { return repository.findAll(); }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public void delete(Long id) { repository.deleteById(id); }
}
