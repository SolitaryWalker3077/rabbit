package com.rabbit.rabbitextensiondemo.controller;

import com.rabbit.rabbitextensiondemo.config.RabbitMQConfig;
import com.rabbit.rabbitextensiondemo.constant.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/ack")
    public  String ack() {
        rabbitTemplate.convertAndSend(Constants.ACK_EXCHANGE,"ack","ack consumer test.....");
        return "消息发送成功";
    }
}
