package com.rabbit.rabbitextensiondemo.config;


import com.rabbit.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayConfig {

    @Bean("delayQueue")
    public Queue delayQueue(){
        return QueueBuilder.durable(Constants.DELAY_QUEUE).build();
    }
    @Bean("delayExchange")
    public Exchange delayExchange(){
        return ExchangeBuilder.directExchange(Constants.DELAY_EXCHANGE).delayed().build();
    }
    @Bean("delayBinding")
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue, @Qualifier("delayExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("delay").noargs();
    }

}
