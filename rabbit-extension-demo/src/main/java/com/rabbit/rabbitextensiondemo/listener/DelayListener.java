package com.rabbit.rabbitextensiondemo.listener;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class DelayListener {
    @RabbitListener(queues = Constants.DELAY_QUEUE)
    public void delayHandMessage(Message message, Channel channel) {
        //消费者逻辑
        System.out.printf("[delay.queue] %tc 接收到消息: %s \n", new Date(), new String(message.getBody(), StandardCharsets.UTF_8));
    }



}
