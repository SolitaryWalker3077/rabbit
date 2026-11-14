package com.rabbit.logisticsserver.listener;

import com.rabbit.orderserver.entity.OrderInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "order.create")
public class OrderListener {

   @RabbitHandler
    public void handMessage(String orderInfo) {
        System.out.println("接收到订单消息: " + orderInfo);
    }

    @RabbitHandler
    public void handleMessage(OrderInfo orderInfo){
        System.out.println("接收到订单消息OrderInfo:"+ orderInfo);
    }

}
