package com.rabbit.demo.controller;

import com.rabbit.demo.constant.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/work")
    public String work() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("", Constants.WORK_QUEUE,"hello spring amqp : work queue..." + i);
        }
        return "发送成功";
    }

    @RequestMapping("/fanout")
    public String fanout() {
        rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE,"","hello spring amqp : fanout...");
        return "发送成功";
    }

    @RequestMapping("/direct/{routingKey}")
    public String direct(@PathVariable("routingKey") String routingKey){
        rabbitTemplate.convertAndSend(Constants.DIRECT_EXCHANGE, routingKey, "hello spring amqp : direct, my routing key is "+routingKey);
        return "发送成功";
    }

    @RequestMapping("/topic/{routingKey}")
    public String topic(@PathVariable("routingKey") String routingKey){
        rabbitTemplate.convertAndSend(Constants.TOPIC_EXCHANGE, routingKey, "hello spring amqp:topic, my routing key is "+routingKey);
        return "发送成功";
    }

}
