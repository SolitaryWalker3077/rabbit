package com.rabbit.demo.config;

import com.rabbit.demo.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean("fanoutQueueBinding1")
    public Binding fanoutQueueBinding1(@Qualifier("fanoutExchange") FanoutExchange fanoutExchange, @Qualifier("fanoutQueue1") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    @Bean("fanoutQueueBinding2")
    public Binding fanoutQueueBinding2(@Qualifier("fanoutExchange") FanoutExchange fanoutExchange, @Qualifier("fanoutQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    //路由模式
    @Bean("directQueue1")
    public Queue directQueue1(){
        return QueueBuilder.durable(Constants.DIRECT_QUEUE1).build();
    }
    @Bean("directQueue2")
    public Queue directQueue2(){
        return QueueBuilder.durable(Constants.DIRECT_QUEUE2).build();
    }
    @Bean("directExchange")
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(Constants.DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean("directQueueBinding1")
    public Binding directQueueBinding1(@Qualifier("directExchange") DirectExchange directExchange, @Qualifier("directQueue1") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("orange");
    }

    @Bean("directQueueBinding2")
    public Binding directQueueBinding2(@Qualifier("directExchange") DirectExchange directExchange, @Qualifier("directQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("black");
    }

    @Bean("directQueueBinding3")
    public Binding directQueueBinding3(@Qualifier("directExchange") DirectExchange directExchange, @Qualifier("directQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("orange");
    }

    //通配符模式
    @Bean("topicQueue1")
    public Queue topicQueue1(){
        return QueueBuilder.durable(Constants.TOPIC_QUEUE1).build();
    }
    @Bean("topicQueue2")
    public Queue topicQueue2(){
        return QueueBuilder.durable(Constants.TOPIC_QUEUE2).build();
    }
    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(Constants.TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean("topicQueueBinding1")
    public Binding topicQueueBinding1(@Qualifier("topicExchange") TopicExchange topicExchange, @Qualifier("topicQueue1") Queue queue){
        return BindingBuilder.bind(queue).to(topicExchange).with("*.orange.*");
    }
    @Bean("topicQueueBinding2")
    public Binding topicQueueBinding2(@Qualifier("topicExchange") TopicExchange topicExchange, @Qualifier("topicQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(topicExchange).with("*.*.rabbit");
    }
    @Bean("topicQueueBinding3")
    public Binding topicQueueBinding3(@Qualifier("topicExchange") TopicExchange topicExchange, @Qualifier("topicQueue2") Queue queue){
        return BindingBuilder.bind(queue).to(topicExchange).with("lazy.#");
    }
}

