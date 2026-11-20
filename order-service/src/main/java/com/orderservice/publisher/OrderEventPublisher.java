package com.orderservice.publisher;

import com.orderservice.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(OrderCreatedEvent event)
    {
        rabbitTemplate.convertAndSend(
                "order.created.queue",
                event);
    }
}
