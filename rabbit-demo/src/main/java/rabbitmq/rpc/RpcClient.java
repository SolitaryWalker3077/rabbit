package rabbitmq.rpc;

import com.rabbitmq.client.*;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * RPC客户端
 * 1.发送请求
 * 2.接收响应
 * */
public class RpcClient {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
        channel.queueDeclare(Constants.RPC_REQUEST_QUEUE,true ,false,false,null);
        channel.queueDeclare(Constants.RPC_RESPONSE_QUEUE,true,false,false,null);
        //发送请求
        String msg = "hello rpc...";
        //设置请求的唯一标识
        String correlationId = UUID.randomUUID().toString();
        //设置请求的相关属性
        AMQP.BasicProperties pros = new AMQP.BasicProperties().builder()
                .correlationId(correlationId)
                .replyTo(Constants.RPC_RESPONSE_QUEUE)
                .build();
        channel.basicPublish("",Constants.RPC_REQUEST_QUEUE,pros,msg.getBytes());

        //接收响应
        //使用阻塞队列来存储响应的信息
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String respMsg = new String(body);
                System.out.println("接收到回调消息: " + respMsg);
                if (correlationId.equals(properties.getCorrelationId())) {
                    //如果correlationId校验一致
                    response.offer(respMsg);
                }
            }
        };
        //消费消息
        channel.basicConsume(Constants.RPC_RESPONSE_QUEUE,true,consumer);
        //获取回调结果
        String result = response.take();
        System.out.println("[RPCClient] Result:" + result);
    }

}
