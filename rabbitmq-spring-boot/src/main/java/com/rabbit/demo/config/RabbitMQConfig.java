package com.rabbit.demo.config;

import com.rabbit.demo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean("workQueue")
    public Queue workQueue() {
        return QueueBuilder.durable(Constants.WORK_QUEUE).build();
    }

}

