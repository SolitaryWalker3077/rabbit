package rabbitmq.work;

import com.rabbitmq.client.*;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.HOST);
        connectionFactory.setPort(Constants.POST);
        connectionFactory.setUsername(Constants.USER_NAME);  //账号
        connectionFactory.setPassword(Constants.PASSWORD);  //密码
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST); //虚拟主机
        Connection connection = connectionFactory.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //声明队列
        //如果队列不存在,则创建,如果队列存在,则不创建
        channel.queueDeclare(Constants.WORK_QUEUE, true, false, false, null);
        //消费消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //从队列中收到消息,
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接受到的消息" + new String(body));
            }
        };
        channel.basicConsume(Constants.WORK_QUEUE, true, consumer);
        Thread.sleep(2000);
        //释放资源
        channel.close();
        connection.close();
    }
}
