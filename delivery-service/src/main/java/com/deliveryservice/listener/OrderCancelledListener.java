package com.deliveryservice.listener;

import com.deliveryservice.dto.OrderCancelledEvent;
import com.deliveryservice.model.Delivery;
import com.deliveryservice.repository.DeliveryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelledListener {

    private final DeliveryRepository repository;

    public OrderCancelledListener(DeliveryRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "order.cancel")
    public void handle(OrderCancelledEvent event) {
        Long orderId = Long.parseLong(event.orderId());
        repository.findByOrderId(orderId).ifPresent(delivery -> {
            delivery.setStatus("CANCELLED");
            repository.save(delivery);
        });
    }
}
