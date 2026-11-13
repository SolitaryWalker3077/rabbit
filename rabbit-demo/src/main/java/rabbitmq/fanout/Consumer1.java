package rabbitmq.fanout;

import com.rabbitmq.client.*;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.HOST);
        connectionFactory.setPort(Constants.POST);
        connectionFactory.setUsername(Constants.USER_NAME);
        connectionFactory.setPassword(Constants.PASSWORD);
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
        Connection connection = connectionFactory.newConnection();
        //开启信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(Constants.FANOUT_QUEUE1,true,false,false,null);

        //消费消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //从队列中收到消息,就会执行该方法

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接受到消息: " + new String(body));
            }
        };
        channel.basicConsume(Constants.FANOUT_QUEUE1,true,consumer);

//        channel.close();
//        connection.close();
    }


}
