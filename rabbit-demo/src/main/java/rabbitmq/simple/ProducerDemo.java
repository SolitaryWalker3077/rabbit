package rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.constant.Constants;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

public class ProducerDemo {
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
        //声明交换机  使用内置的交换机
        //如果队列不存在,则创建队列,如果队列存在,则不创建
        channel.queueDeclare("hello",true,false,false,null);
        //发送消息
        for (int i = 0; i < 10; i++) {
            String msg = "hello rabbitMq" + i;
            channel.basicPublish("","hello",null,msg.getBytes());
        }

        System.out.println("消息发送成功");
        //释放资源
        channel.close();
        connection.close();
    }
}
