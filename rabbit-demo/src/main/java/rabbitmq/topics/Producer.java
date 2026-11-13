package rabbitmq.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 通配符模式生产者
 * */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.HOST);
        connectionFactory.setPort(Constants.POST);
        connectionFactory.setUsername(Constants.USER_NAME);  //账号
        connectionFactory.setPassword(Constants.PASSWORD);  //密码
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST); //虚拟主机
        Connection connection = connectionFactory.newConnection();
        //开启信道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(Constants.TOPICS_EXCHANGE, BuiltinExchangeType.TOPIC,true);
        //声明队列
        channel.queueDeclare(Constants.TOPICS_QUEUE1,true,false,false,null);
        channel.queueDeclare(Constants.TOPICS_QUEUE2,true,false,false,null);
        //绑定交换机和队列
        channel.queueBind(Constants.TOPICS_QUEUE1,Constants.TOPICS_EXCHANGE,"*.a.*");
        channel.queueBind(Constants.TOPICS_QUEUE2,Constants.TOPICS_EXCHANGE,"*.*.b");
        channel.queueBind(Constants.TOPICS_QUEUE2,Constants.TOPICS_EXCHANGE,"c.#");
        //发送消息
        String msg_a = "hello topics,my routingKey is ae.a.f";
        channel.basicPublish(Constants.TOPICS_EXCHANGE,"ae.a.f",null,msg_a.getBytes()); //满足*.a.*,转发到Q1

        String msg_b = "hello topics,my routingKey is af.a.b";
        channel.basicPublish(Constants.TOPICS_EXCHANGE,"af.a.b",null,msg_b.getBytes());//满足*.a.*和*.*.b ,转发到Q1和Q2

        String msg_c = "hello topics,my routingKey is c.ef.d";
        channel.basicPublish(Constants.TOPICS_EXCHANGE,"c.ef.d",null,msg_c.getBytes());//,满足c.# 转发到Q2

        System.out.println("消息发送成功");

        channel.close();
        connection.close();
    }
}

