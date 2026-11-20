package com.orderservice.controller;

import com.orderservice.model.Order;
import com.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@RequestBody Order order) { return service.placeOrder(order); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) { return service.create(order); }

    @GetMapping
    public List<Order> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) { return service.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
