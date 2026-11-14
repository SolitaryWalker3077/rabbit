package com.rabbit.logisticsserver;

import com.rabbit.logisticsserver.config.RabbitMQConfig;
import com.rabbit.orderserver.entity.OrderInfo;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

import static org.junit.jupiter.api.Assertions.*;

class OrderMessageConversionTests {
    private final JacksonJsonMessageConverter producer = new JacksonJsonMessageConverter();
    private final JacksonJsonMessageConverter consumer = new RabbitMQConfig().jacksonJsonMessageConverter();

    @Test
    void convertsOrderObjectUsingProducerTypeHeader() {
        OrderInfo order = new OrderInfo();
        order.setOrderId("test-order-1");
        order.setName("商品");
        var message = producer.toMessage(order, new MessageProperties());
        var received = assertInstanceOf(OrderInfo.class, consumer.fromMessage(message));
        assertEquals(order, received);
    }

    @Test
    void stillConvertsStringMessages() {
        var message = producer.toMessage("订单信息", new MessageProperties());
        assertEquals("订单信息", consumer.fromMessage(message));
    }
}
