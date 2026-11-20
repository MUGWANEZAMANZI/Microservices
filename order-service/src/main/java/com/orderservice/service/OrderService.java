package com.orderservice.service;

import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        order.setStatus("PENDING");
        return repository.save(order);
    }
    public List<Order> findAll() { return repository.findAll(); }
    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
