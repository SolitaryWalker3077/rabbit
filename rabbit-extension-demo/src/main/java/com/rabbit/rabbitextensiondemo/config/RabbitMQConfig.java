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
        /**
         * public static QueueBuilder durable(String name) {
         * 		return new QueueBuilder(name).setDurable();
         * 	    }
         *
         * private QueueBuilder setDurable() {
         * 		this.durable = true;
         * 		return this;
         * 	    }
         * 所以默认用durable是创建持久化队列
         * */
        return QueueBuilder.durable(Constants.ACK_QUEUE).build();
        //return QueueBuilder.nonDurable(Constants.ACK_QUEUE).build();   //创建非持久化队列
    }

    @Bean("directExchange")
    public DirectExchange directExchange() {
        // 默认是持久化的交换机
        return ExchangeBuilder.directExchange(Constants.ACK_EXCHANGE).build();
        //设置非持久化的交换机,将durable的参数改为false
        //return ExchangeBuilder.directExchange(Constants.ACK_EXCHANGE).durable(false).build();
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

    //非持久化
    @Bean("presQueue")
    public Queue presQueue() {
        return QueueBuilder.nonDurable(Constants.PRES_QUEUE).build();
    }

    @Bean("presExchange")
    public DirectExchange presExchange() {
        return ExchangeBuilder.directExchange(Constants.PRES_EXCHANGE).durable(false).build();
    }

    public Binding presBinding(@Qualifier("presQueue") Queue queue,@Qualifier("presExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("pres").noargs();
    }

    //发送方确认
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(Constants.CONFIRM_QUEUE).build();
    }
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(Constants.CONFIRM_EXCHANGE).build();
    }
    @Bean("confirmBinding")
    public Binding confirmBinding(@Qualifier("confirmQueue") Queue queue, @Qualifier("confirmExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("confirm").noargs();
    }

    //重试机制
    @Bean("retryQueue")
    public Queue retryQueue() {
        return QueueBuilder.durable(Constants.RETRY_QUEUE).build();
    }

    @Bean("retryExchange")
    public DirectExchange retryExchange(){
        return ExchangeBuilder.directExchange(Constants.RETRY_EXCHANGE).build();
    }
    @Bean("retryBinding")
    public Binding retryBinding(@Qualifier("retryQueue") Queue queue, @Qualifier("retryExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("retry").noargs();
    }

    //ttl
    //未设置ttl
    @Bean("ttlQueue")
    public Queue ttlQueue() {
        return QueueBuilder.durable(Constants.TTL_QUEUE).build();
    }
    //设置了ttl
    @Bean("ttlQueue2")
    public Queue ttlQueue2() {
        return QueueBuilder.durable(Constants.TTL_QUEUE2).ttl(20000).build(); //ttl 设置20s

    }
    @Bean("ttlExchange")
    public DirectExchange ttlExchange() {
        return ExchangeBuilder.directExchange(Constants.TTL_EXCHANGE).build();
    }
    @Bean("ttlBinding")
    public Binding ttlBinding(@Qualifier("ttlQueue") Queue queue ,@Qualifier("ttlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }

    @Bean("ttlBinding2")
    public Binding ttlBinding2(@Qualifier("ttlQueue2") Queue queue ,@Qualifier("ttlExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
    }


}
