package rabbitmq.publisher.confirms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.constant.Constants;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class PublisherConfirms {

    private static final Integer MESSAGE_COUNT = 200;
    static Connection createConnection() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.HOST);
        connectionFactory.setPort(Constants.POST);
        connectionFactory.setUsername(Constants.USER_NAME);  //账号
        connectionFactory.setPassword(Constants.PASSWORD);  //密码
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST); //虚拟主机
        return connectionFactory.newConnection();
    }

    public static void main(String[] args) throws Exception{
        //Strategy #1: Publishing Messages Individually
        //单独确认
        publishingMessagesIndividually();
        //Strategy #2: Publishing Messages in Batches
        //批量确认
        publishingMessagesInBatches();

        //Strategy #3: Handling Publisher Confirms Asynchronously
        //异步确认
        handlingPublisherConfirmsAsynchronously();

    }

    /**
     * 异步确认
     * */
    private static void handlingPublisherConfirmsAsynchronously() throws Exception{
        try (Connection connection = createConnection()){
            //1. 开启信道
            Channel channel = connection.createChannel();
            //2. 设置信道为confirm模式
            channel.confirmSelect();
            //3. 声明队列
            channel.queueDeclare(Constants.PUBLISHER_CONFIRMS_QUEUE3, true, false, false, null);
            //4. 监听confirm
            //集合中存储的是未确认的消息ID
            long start = System.currentTimeMillis();
            SortedSet<Long> confirmSeqNo = Collections.synchronizedSortedSet(new TreeSet<>());

            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple){
                        confirmSeqNo.headSet(deliveryTag+1).clear();
                    }else {
                        confirmSeqNo.remove(deliveryTag);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple){
                        confirmSeqNo.headSet(deliveryTag+1).clear();
                    }else {
                        confirmSeqNo.remove(deliveryTag);
                    }
                    //业务需要根据实际场景进行处理, 比如重发, 此处代码省略
                }
            });
            //5. 发送消息
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String msg = "hello publisher confirms"+i;
                long seqNo = channel.getNextPublishSeqNo();
                channel.basicPublish("",Constants.PUBLISHER_CONFIRMS_QUEUE3, null, msg.getBytes());
                confirmSeqNo.add(seqNo);
            }
            while (!confirmSeqNo.isEmpty()){
                Thread.sleep(10);
            }
            long end = System.currentTimeMillis();
            System.out.printf("异步确认策略, 消息条数: %d, 耗时: %d ms \n",MESSAGE_COUNT, end-start);
        }


    }

    /**
     * 批量确认
     * */
    private static void publishingMessagesInBatches() throws Exception {
        try (Connection connection = createConnection()) {
            //开启信道
            Channel channel = connection.createChannel();
            //设置信道为confirm模式
            channel.confirmSelect();
            //声明队列
            channel.queueDeclare(Constants.PUBLISHER_CONFIRMS_QUEUE2,true,false,false,null);
            //发送消息,并进行确认
            long start = System.currentTimeMillis();
            int batchSize = 100;
            int outstandingMessageCount = 0;
            for (int i = 0; i  < MESSAGE_COUNT; i++) {
                String msg = "hello publisher confirms"+i;
                channel.basicPublish("",Constants.PUBLISHER_CONFIRMS_QUEUE2, null, msg.getBytes());
                outstandingMessageCount++;
                if (outstandingMessageCount==batchSize) {
                    channel.waitForConfirmsOrDie(5000);
                    outstandingMessageCount = 0;
                }
            }
            if (outstandingMessageCount>0){
                channel.waitForConfirmsOrDie(5000);
            }
            long end = System.currentTimeMillis();
            System.out.printf("批量确认策略, 消息条数: %d, 耗时: %d ms \n",MESSAGE_COUNT, end-start);
        }
    }


    /**
     * 单独确认
     * */
    private static void publishingMessagesIndividually() throws Exception {
        try (Connection connection = createConnection()) {
            //开启信道
            Channel channel = connection.createChannel();
            //设置信道为confirm模式
            channel.confirmSelect();
            //声明队列
            channel.queueDeclare("",true,false,false,null);
            //发送消息,并等待确认
            long start = System.currentTimeMillis();
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String msg = "hello publisher confirms"+i;
                channel.basicPublish("",Constants.PUBLISHER_CONFIRMS_QUEUE1, null, msg.getBytes());
                //等待确认
                channel.waitForConfirmsOrDie(5000);
            }
            long end = System.currentTimeMillis();
            System.out.printf("单独确认策略, 消息条数: %d, 耗时: %d ms \n",MESSAGE_COUNT, end-start);
        }
    }
}
