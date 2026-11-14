package com.rabbit.orderserver.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/create")
    public String create() {
        String orderId = UUID.randomUUID().toString();
        rabbitTemplate.convertAndSend("","order.create","订单信息,加订单ID" + orderId);
        return "下单成功";
    }

}
