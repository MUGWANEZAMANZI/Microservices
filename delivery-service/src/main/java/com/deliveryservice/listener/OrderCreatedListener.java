package com.deliveryservice.listener;

import com.deliveryservice.dto.OrderCreatedEvent;
import com.deliveryservice.model.Delivery;
import com.deliveryservice.service.DeliveryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final DeliveryService deliveryService;

    public OrderCreatedListener(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RabbitListener(queues = "order.created.queue")
    public void handle(OrderCreatedEvent event) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(Long.parseLong(event.orderId()));
        delivery.setStatus("ASSIGNED");
        deliveryService.create(delivery);
    }
}
