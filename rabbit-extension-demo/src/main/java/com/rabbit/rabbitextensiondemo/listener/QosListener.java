package com.rabbit.rabbitextensiondemo.listener;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QosListener {

    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void handMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消费者逻辑
            System.out.printf("111接收到消息: %s, deliveryTag: %d \n", new String(message.getBody(), "UTF-8"), message.getMessageProperties().getDeliveryTag());

            Thread.sleep(2000);
//            System.out.println("业务处理完成");
            //肯定确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliveryTag, false, true);
        }

    }


    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void handMessage2(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消费者逻辑
            System.out.printf("222接收到消息: %s, deliveryTag: %d \n", new String(message.getBody(), "UTF-8"), message.getMessageProperties().getDeliveryTag());

            Thread.sleep(1000);
//            System.out.println("业务处理完成");
            //肯定确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliveryTag, false, true);
        }

    }
}
