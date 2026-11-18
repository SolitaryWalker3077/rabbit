package com.rabbit.rabbitextensiondemo.config;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class QosConfig {

    @Bean("qosQueue")
    public Queue qosQueue() {
        return QueueBuilder.durable(Constants.QOS_QUEUE).build();
    }
    @Bean("qosExchange")
    public Exchange qosExchange() {
        return ExchangeBuilder.directExchange(Constants.QOS_EXCHANGE).build();
    }
    @Bean("qosBinding")
    public Binding qosBinding(@Qualifier("qosQueue") Queue queue,@Qualifier("qosExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("qos").noargs();
    }

}
