package rabbitmq.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
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
        /**
         * Exchange.DeclareOk exchangeDeclare(String exchange,
         *         BuiltinExchangeType type,
         *         boolean durable,
         *         boolean autoDelete,
         *         boolean internal,
         *         Map<String, Object> arguments) throws IOException;
         *
         *  参数说明: exchange: 交换机名字
         *            type: 交换机类型   DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers"),
         *                             CONSISTENT_HASH("x-consistent-hash"), MODULUS_HASH("x-modulus-hash"),
         *                             LOCAL_RANDOM("x-local-random"), RANDOM("x-random");
         *            durable: 是否可持久化
         *            autoDelete: 是否自动删除
         *            internal:是否内部使用,如果内部使用,客户端无法正常去使用
         * */
        channel.exchangeDeclare(Constants.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT,true);
        //声明队列
        //如果队列不存在,则创建队列,如果队列存在,则不创建
        channel.queueDeclare(Constants.FANOUT_QUEUE1,true,false,false,null);
        channel.queueDeclare(Constants.FANOUT_QUEUE2,true,false,false,null);
        //交换机和队列进行绑定
        channel.queueBind(Constants.FANOUT_QUEUE1,Constants.FANOUT_EXCHANGE,"");
        channel.queueBind(Constants.FANOUT_QUEUE2,Constants.FANOUT_EXCHANGE,"");
        //发送消息
            String msg = "hello fanout.....";
            channel.basicPublish(Constants.FANOUT_EXCHANGE,"",null,msg.getBytes());

        System.out.println("消息发送成功");
        //释放资源
        channel.close();
        connection.close();
    }

}
