package com.deliveryservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String ORDER_QUEUE = "order.created.queue";
    public static final String ORDER_CANCELLED_QUEUE = "order.cancel";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE);
    }

    @Bean
    public Queue orderCancelledQueue() {
        return new Queue(ORDER_CANCELLED_QUEUE);
    }

    public static final String DELIVERY_STATUS_QUEUE = "delivery.status.updated.queue";

    @Bean
    public Queue deliveryStatusQueue() {
        return new Queue(DELIVERY_STATUS_QUEUE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
