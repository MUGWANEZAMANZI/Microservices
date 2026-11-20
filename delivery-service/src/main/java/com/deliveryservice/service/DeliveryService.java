package com.deliveryservice.service;

import com.deliveryservice.model.Delivery;
import com.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery create(Delivery delivery) {
        delivery.setStatus("PENDING");
        return repository.save(delivery);
    }
    public List<Delivery> findAll() { return repository.findAll(); }
    public Delivery findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
