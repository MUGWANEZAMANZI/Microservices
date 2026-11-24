package com.deliveryservice.service;

import com.deliveryservice.client.DeliveryClient;
import com.deliveryservice.dto.OrderResponse;
import com.deliveryservice.model.Delivery;
import com.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;
    private final DeliveryClient deliveryClient;

    public DeliveryService(DeliveryRepository repository,
                           DeliveryClient deliveryClient) {
        this.repository = repository;
        this.deliveryClient = deliveryClient;
    }

    public Delivery create(Delivery delivery) {
        delivery.setStatus("PENDING");
        return repository.save(delivery);
    }
    public List<Delivery> findAll() {
        List<OrderResponse> orders = deliveryClient.getAllOrders();
        if (orders.isEmpty()) {
            throw new RuntimeException("Order service is unavailable. Please try again later.");
        }

        return repository.findAll(); }
    public Delivery findById(Long id) {
        OrderResponse order = deliveryClient.getOrderById(id);
        if(order == null) {
            throw new RuntimeException("Order service is unavailable. Please try again later.");
        }


        return repository.findById(id).orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
