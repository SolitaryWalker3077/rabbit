package com.rabbit.demo.listener;

import com.rabbit.demo.constant.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectListener {

    @RabbitListener(queues = Constants.DIRECT_QUEUE1)
    public void queueListener1(String message){
        System.out.println("队列["+Constants.DIRECT_QUEUE1+"] 接收到消息:" +message);
    }

    @RabbitListener(queues = Constants.DIRECT_QUEUE2)
    public void queueListener2(String message){
        System.out.println("队列["+ Constants.DIRECT_QUEUE2+"] 接收到消息:" +message);
    }

}
