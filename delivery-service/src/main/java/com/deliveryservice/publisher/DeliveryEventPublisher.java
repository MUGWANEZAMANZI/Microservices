package com.deliveryservice.publisher;

import com.deliveryservice.dto.DeliveryStatusUpdatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeliveryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public DeliveryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishStatusUpdated(DeliveryStatusUpdatedEvent event) {
        rabbitTemplate.convertAndSend("delivery.status.updated.queue", event);
    }
}
