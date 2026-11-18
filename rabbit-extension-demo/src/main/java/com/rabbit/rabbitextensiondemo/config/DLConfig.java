package com.rabbit.rabbitextensiondemo.config;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLConfig {

    //正常交换机和队列
    @Bean("normalQueue")
    public Queue normalQueue() {
        return QueueBuilder.durable(Constants.NORMAL_QUEUE)
                .deadLetterExchange(Constants.DL_EXCHANGE)
                .deadLetterRoutingKey("dlx")
                .ttl(10000)   //给给正常队列设置过期时间,如果超过过期时间内没有被处理就进入死信队列当中
                .maxLength(10L)
                .build();
    }
    @Bean("normalExchange")
    public Exchange normalExchange() {
        return ExchangeBuilder.directExchange(Constants.NORMAL_EXCHANGE).build();
    }
    @Bean("normalBinding")
    public Binding normalBinding(@Qualifier("normalQueue") Queue queue,@Qualifier("normalExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("normal").noargs();
    }

    //死信交换机和队列
    @Bean("dlQueue")
    public Queue dlQueue() {
        return QueueBuilder.durable(Constants.DL_QUEUE).build();
    }
    @Bean("dlExchange")
    public Exchange dlExchange() {
        return ExchangeBuilder.directExchange(Constants.DL_EXCHANGE).build();
    }
    @Bean("dlBinding")
    public Binding dlBinding(@Qualifier("dlQueue") Queue queue,@Qualifier("dlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dlx").noargs();
    }

}
