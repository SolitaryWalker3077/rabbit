package com.rabbit.rabbitextensiondemo.config;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //消息确认
    //声明一个队列
    @Bean("ackQueue")
    public Queue ackQueue() {
        return QueueBuilder.durable(Constants.ACK_QUEUE).build();
    }

    @Bean("directExchange")
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(Constants.ACK_EXCHANGE).build();
    }


    /**
     * 如果没有指定交换机类型的话,需要在with后面加上一个noargs
     *
     * .noargs()方法:
     * RabbitMQ 中，交换机和队列建立绑定关系时，除了 RoutingKey，还可以携带额外的绑定参数。
     *  例如：
     * - Direct Exchange：主要通过 RoutingKey 匹配。
     * - Topic Exchange：主要通过 RoutingKey 的通配符匹配。
     * - Headers Exchange：可以通过绑定参数中的 Header 进行匹配。
     *
     *
     * .noargs() 表示：
     *  这个绑定关系不需要额外的 Arguments 参数。
     *
     *  当交换机使用通用的 Exchange 类型声明时，
     *  BindingBuilder 会调用通用交换机的绑定配置方法，with() 返回的是参数配置器，
     *  因此需要通过 .noargs() 声明不需要额外的绑定参数，从而完成 Binding 对象的构建。
     * */
//    @Bean("ackBinding")
//    public Binding ackBinding(Exchange directExchange,Queue queue) {
//        //这里的 .noargs() 是 Spring AMQP 中用于**结束 Binding 绑定配置，并指定该绑定不需要额外参数（Arguments）**的方法。
//        return BindingBuilder.bind(queue).to(directExchange).with("ack").noargs();
//    }

    @Bean("ackBinding")
    public Binding ackBinding(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("ackQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("ack");
    }



}
