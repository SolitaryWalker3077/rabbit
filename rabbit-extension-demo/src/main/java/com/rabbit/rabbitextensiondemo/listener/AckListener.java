package com.rabbit.rabbitextensiondemo.listener;

import com.rabbit.rabbitextensiondemo.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AckListener {

    @RabbitListener(queues = Constants.ACK_QUEUE)
    public void handMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //消费者逻辑
            System.out.printf("接收到消息: %s, deliveryTag: %d \n",
                    new String(message.getBody(), StandardCharsets.UTF_8),
                    message.getMessageProperties().getDeliveryTag());

            //进行业务逻辑处理
            System.out.println("业务逻辑处理");
            //int num = 3 / 0;
            System.out.println("业务处理完成");
            channel.basicAck(deliveryTag, false);  //肯定确认 当策略是AcknowledgeMode.MANUAL 时,需要进行手动的确认
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);   //否定确认  requeue 参数 : 是否重新入队
        }
    }
}
