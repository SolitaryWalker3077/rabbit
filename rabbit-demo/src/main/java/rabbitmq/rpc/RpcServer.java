package rabbitmq.rpc;


import com.rabbitmq.client.*;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RPC服务端
 * 1.接收请求
 * 2.发送响应
 * */
public class RpcServer {
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
        //接收请求
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String request = new String(body,"UTF-8");
                System.out.println("接收到请求:" + request);
                String response = "针对request" + request + ",响应成功";
                AMQP.BasicProperties replyPros = new AMQP.BasicProperties().builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                channel.basicPublish("",Constants.RPC_RESPONSE_QUEUE,replyPros,response.getBytes());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(Constants.RPC_REQUEST_QUEUE,false,consumer);
    }
}
