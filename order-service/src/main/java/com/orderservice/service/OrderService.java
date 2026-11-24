package com.orderservice.service;

import com.orderservice.client.CustomerClient;
import com.orderservice.client.RestaurantClient;
import com.orderservice.dto.CustomerResponse;
import com.orderservice.dto.OrderCreatedEvent;
import com.orderservice.dto.RestaurantResponse;
import com.orderservice.publisher.OrderEventPublisher;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final RestaurantClient restaurantClient;
    private final OrderEventPublisher publisher;


    public OrderService(OrderRepository repository,
                        CustomerClient customerClient,
                        RestaurantClient restaurantClient,
                        OrderEventPublisher publisher) {
        this.repository = repository;
        this.customerClient = customerClient;
        this.restaurantClient = restaurantClient;
        this.publisher = publisher;
    }

    public Order placeOrder(Order order) {


        CustomerResponse cust = customerClient.getCustomer(order.getCustomerId());
        if(cust == null){
            throw new RuntimeException("Customer service is unavailable. Please try again later.");
        }
        // Check if the restaurant exists using the RestaurantClient
        RestaurantResponse res = restaurantClient.getRestaurant(order.getRestaurantId());

        if (res == null){
            throw new RuntimeException("Restaurant service is unavailable. Please try again later.");
        }
        order.setStatus("PENDING");
        Order saved = repository.save(order);
        publisher.publish(new OrderCreatedEvent(
                saved.getId().toString(),
                saved.getCustomerId(),
                saved.getRestaurantId(),
                saved.getStatus()
        ));
        return saved;
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
