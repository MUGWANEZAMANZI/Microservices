package com.deliveryservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    // Main queues
    public static final String ORDER_QUEUE           = "order.created.queue";
    public static final String ORDER_CANCELLED_QUEUE = "order.cancel";
    public static final String DELIVERY_STATUS_QUEUE = "delivery.status.updated.queue";

    // Dead letter infrastructure
    public static final String DLX                   = "dead-letter-exchange";
    public static final String ORDER_CREATED_DLQ     = "order.created.dlq";
    public static final String ORDER_CANCELLED_DLQ   = "order.cancel.dlq";
    public static final String DELIVERY_STATUS_DLQ   = "delivery.status.updated.dlq";

    // --- Dead Letter Exchange ---
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    // --- Dead Letter Queues ---
    @Bean
    public Queue orderCreatedDlq() {
        return new Queue(ORDER_CREATED_DLQ);
    }

    @Bean
    public Queue orderCancelledDlq() {
        return new Queue(ORDER_CANCELLED_DLQ);
    }

    @Bean
    public Queue deliveryStatusDlq() {
        return new Queue(DELIVERY_STATUS_DLQ);
    }

    // --- Bind DLQs to DLX (routing key = original queue name) ---
    @Bean
    public Binding orderCreatedDlqBinding() {
        return BindingBuilder.bind(orderCreatedDlq()).to(deadLetterExchange()).with(ORDER_QUEUE);
    }

    @Bean
    public Binding orderCancelledDlqBinding() {
        return BindingBuilder.bind(orderCancelledDlq()).to(deadLetterExchange()).with(ORDER_CANCELLED_QUEUE);
    }

    @Bean
    public Binding deliveryStatusDlqBinding() {
        return BindingBuilder.bind(deliveryStatusDlq()).to(deadLetterExchange()).with(DELIVERY_STATUS_QUEUE);
    }

    // --- Main queues configured to route failures to DLX ---
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .build();
    }

    @Bean
    public Queue orderCancelledQueue() {
        return QueueBuilder.durable(ORDER_CANCELLED_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .build();
    }

    @Bean
    public Queue deliveryStatusQueue() {
        return QueueBuilder.durable(DELIVERY_STATUS_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
