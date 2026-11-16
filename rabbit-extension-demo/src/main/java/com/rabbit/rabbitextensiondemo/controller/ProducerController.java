package com.rabbit.rabbitextensiondemo.controller;

import com.rabbit.rabbitextensiondemo.config.RabbitMQConfig;
import com.rabbit.rabbitextensiondemo.constant.Constants;
import jakarta.annotation.Resource;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "confirmRabbitTemplate")
    private RabbitTemplate confirmRabbitTemplate;

    @RequestMapping("/ack")
    public String ack() {
        rabbitTemplate.convertAndSend(Constants.ACK_EXCHANGE, "ack", "ack consumer test.....");
        return "消息发送成功";
    }

    @RequestMapping("/pres")
    public String pres() {
        Message message = new Message("Presistent test...".getBytes(), new MessageProperties());
        //消息非持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        //消息持久化
//        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        System.out.println(message);
        rabbitTemplate.convertAndSend(Constants.PRES_EXCHANGE, "pres", message);
        return "消息发送成功";
    }

    @RequestMapping("/confirm")
    public String confirm() {
        CorrelationData correlationData = new CorrelationData("1");
        confirmRabbitTemplate.convertAndSend(Constants.CONFIRM_EXCHANGE, "confirm111", "confirm test...", correlationData);
        return "消息发送成功";
    }

    @RequestMapping("/returns")
    public String returns() {
        CorrelationData correlationData = new CorrelationData("5");
        confirmRabbitTemplate.convertAndSend(Constants.CONFIRM_EXCHANGE, "confirm111", "returns test...", correlationData);
        return "消息发送成功";
    }

}
