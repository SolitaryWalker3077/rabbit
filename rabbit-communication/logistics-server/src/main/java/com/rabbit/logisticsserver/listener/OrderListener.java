package com.rabbit.logisticsserver.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @RabbitListener(queues = "order.create")
    public void handMessage(String orderInfo) {
        System.out.println("接收到订单消息: " + orderInfo);
    }

}
