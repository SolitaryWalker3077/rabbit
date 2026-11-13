package rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
        channel.queueDeclare(Constants.WORK_QUEUE, true, false, false, null);
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
            String msg = "hello work queue...." + i;
            channel.basicPublish("", Constants.WORK_QUEUE, null, msg.getBytes());
        }

        System.out.println("消息发送成功");
        //释放资源
        channel.close();
        connection.close();
    }
}
