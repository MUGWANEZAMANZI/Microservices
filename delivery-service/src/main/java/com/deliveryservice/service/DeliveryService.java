package com.deliveryservice.service;

import com.deliveryservice.client.DeliveryClient;
import com.deliveryservice.dto.DeliveryStatusUpdatedEvent;
import com.deliveryservice.dto.OrderResponse;
import com.deliveryservice.model.Delivery;
import com.deliveryservice.publisher.DeliveryEventPublisher;
import com.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;
    private final DeliveryClient deliveryClient;
    private final DeliveryEventPublisher publisher;

    public DeliveryService(DeliveryRepository repository,
                           DeliveryClient deliveryClient,
                           DeliveryEventPublisher publisher) {
        this.repository = repository;
        this.deliveryClient = deliveryClient;
        this.publisher = publisher;
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
    public Delivery updateStatus(Long id, String status, String driverName) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found: " + id));
        delivery.setStatus(status);
        if (driverName != null) delivery.setDriverName(driverName);
        Delivery saved = repository.save(delivery);
        publisher.publishStatusUpdated(new DeliveryStatusUpdatedEvent(
                saved.getId(), saved.getOrderId(), saved.getDriverName(), saved.getStatus()));
        return saved;
    }

    public void delete(Long id) { repository.deleteById(id); }
}
