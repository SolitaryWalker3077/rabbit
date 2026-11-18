package com.rabbit.rabbitextensiondemo.controller;

import com.rabbit.rabbitextensiondemo.config.RabbitMQConfig;
import com.rabbit.rabbitextensiondemo.constant.Constants;
import jakarta.annotation.Resource;
import org.jspecify.annotations.Nullable;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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


    @RequestMapping("/retry")
    public String retry() {
        System.out.println("retry...");
        rabbitTemplate.convertAndSend(Constants.RETRY_EXCHANGE,"retry","retry test...");
        return "消息发送成功";
    }

    @RequestMapping("/ttl")
    public String ttl() {
        System.out.println("ttl...");
        //设置消息的TTL
//        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setExpiration("10000");
//                return message;
//            }
//        };
//        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE,"ttl","ttl test...",messagePostProcessor);

        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE, "ttl", "ttl test...",
                message -> {
                    message.getMessageProperties().setExpiration("10000"); //单位ms,过期时间10s
                    return message;
                });
        return "消息发送成功";
    }

    @RequestMapping("/ttl2")
    public String ttl2() {
        System.out.println("ttl2.....");
        rabbitTemplate.convertAndSend(Constants.TTL_EXCHANGE,"ttl","ttl2 test....");
        return "消息发送成功";
    }

    @RequestMapping("/dl")
    public String dl() {
        System.out.println("dl...");
        //发送普通消息
        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE, "normal", "dl test...");
        System.out.printf("%tc 消息发送成功 \n", new Date());
//        //测试队列长度
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE, "normal", "dl test..."+i);
//        }
        return "消息发送成功";
    }


    /**
     * 延迟队列
     *  通过TTL+死信队列
     *  1.设置队列的TTL
     *  2.设置消息的TTL
     *
     * */
    @RequestMapping("/delay")
    public String delay() {
        System.out.println("delay...");

        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE, "normal", "ttl test 10s...", message -> {
            message.getMessageProperties().setExpiration("10000");  //单位: 毫秒, 过期时间为10s
            return message;
        });

        rabbitTemplate.convertAndSend(Constants.NORMAL_EXCHANGE, "normal", "ttl test 30s...", message -> {
            message.getMessageProperties().setExpiration("30000");  //单位: 毫秒, 过期时间为30s
            return message;
        });

        System.out.printf("%tc 消息发送成功 \n", new Date());
        return "消息发送成功";
    }

    /**
     * 延迟插件的使用
     * */
    @RequestMapping("/delay2")
    public String delay2() {
        System.out.println("delay2...");

        rabbitTemplate.convertAndSend(Constants.DELAY_EXCHANGE, "delay", "delay test 30s...", message -> {
            message.getMessageProperties().setDelayLong(30000L);  //单位: 毫秒, 过期时间为30s
            return message;
        });
        rabbitTemplate.convertAndSend(Constants.DELAY_EXCHANGE, "delay", "delay test 10s...", message -> {
            message.getMessageProperties().setDelayLong(10000L);  //单位: 毫秒, 延迟时间为10s
            return message;
        });

        System.out.printf("%tc 消息发送成功 \n", new Date());
        return "消息发送成功";
    }

}
