package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

public class ProducerDemo {
    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("43.140.242.83");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");  //账号
        connectionFactory.setPassword("admin");  //密码
        connectionFactory.setVirtualHost("study_rabbit"); //虚拟主机
        Connection connection = connectionFactory.newConnection();

        //开启信道
        Channel channel = connection.createChannel();
        //声明交换机  使用内置的交换机
        //声明队列
        /**
         * Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
         *                                  Map<String, Object> arguments) throws IOException;
         *
         *  参数说明:
         *          queue: 队列名称
         *          durable: 是否可持久化
         *          exclusive: 是否独占
         *          autoDelete: 是否自动删除
         *          arguments: 参数
         * */
        channel.queueDeclare("hello",true,false,false,null);
        //发送消息
        /**
         *   void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
         *   参数说明:
         *          exchange: 交换机名称
         *         routingKey: 内置交换机,routingKey和队列名称保持一致
         *         props: 配置属性
         *         body: 发送的消息
         * */
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
