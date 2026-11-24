package com.orderservice.publisher;

import com.orderservice.dto.OrderCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCancelPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(OrderCancelEvent event) {
        rabbitTemplate.convertAndSend("order.cancel", event);
    }

}