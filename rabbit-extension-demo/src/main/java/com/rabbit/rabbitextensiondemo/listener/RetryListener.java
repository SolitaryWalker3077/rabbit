package com.rabbit.rabbitextensiondemo.listener;


import com.rabbit.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class RetryListener {
    @RabbitListener(queues = Constants.RETRY_QUEUE)
    public void handlerMessage(Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.printf("["+Constants.RETRY_QUEUE+"]接收到消息: %s, deliveryTag: %s \n", new String(message.getBody(), StandardCharsets.UTF_8), deliveryTag);
        int num = 3/0;
        System.out.println("业务处理完成");
    }

//    @RabbitListener(queues = Constants.RETRY_QUEUE)
//    public void handlerMessage(Message message, Channel channel) throws Exception {
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        System.out.printf("[" + Constants.RETRY_QUEUE + "]接收到消息: %s, deliveryTag: %s \n", new String(message.getBody(), "UTF-8"), deliveryTag);
//        try {
//            int num = 3 / 0;
//            System.out.println("业务处理完成");
//            channel.basicAck(deliveryTag, false);
//        } catch (Exception e) {
//            channel.basicNack(deliveryTag, false, true);
//        }
//    }
}
