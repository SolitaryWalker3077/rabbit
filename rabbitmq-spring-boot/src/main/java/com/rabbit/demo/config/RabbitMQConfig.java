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

    @Bean("fanoutQueue1")
    public Queue fanoutQueue1(){
        return QueueBuilder.durable(Constants.FANOUT_QUEUE1).build();
    }

    @Bean("fanoutQueue2")
    public Queue fanoutQueue2(){
        return QueueBuilder.durable(Constants.FANOUT_QUEUE2).build();
    }

    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(Constants.FANOUT_EXCHANGE).durable(true).build();
    }

}

