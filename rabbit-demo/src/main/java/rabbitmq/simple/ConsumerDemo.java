package rabbitmq.simple;

import com.rabbitmq.client.*;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerDemo {


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
        channel.queueDeclare("hello",true,false,false,null);
        //消费消息
        /**
         * String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
         *
         * 参数说明:
         *      queue: 队列名称
         *      autoAck: 是否自动确认
         *      callback: 接受到消息后执行的逻辑
         * */
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             *  从队列当中接收到消息,就会执行该方法
             *  consumerTag: 消费者标签, 通常是消费者在订阅队列时指定的.
             *  envelope: 包含消息的封包信息，如队列名称, 交换机等.
             *  properties : ⼀些配置信息
             *  body : 消息的具体内容
             * */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接受到的消息" + new String(body));
            }
        };
        channel.basicConsume("hello",true,consumer);
        Thread.sleep(2000);
        //释放资源
        channel.close();
        connection.close();
    }
}
