package com.rabbit.orderserver.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean("orderQueue")
    public Queue OrderQueue() {
        return QueueBuilder.durable("order.create").build();
    }


}
