package com.deliveryservice.controller;

import com.deliveryservice.model.Delivery;
import com.deliveryservice.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery create(@RequestBody Delivery delivery) { return service.create(delivery); }

    @GetMapping
    public List<Delivery> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Delivery findById(@PathVariable Long id) { return service.findById(id); }

    @PatchMapping("/{id}/status")
    public Delivery updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.updateStatus(id, body.get("status"), body.get("driverName"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
