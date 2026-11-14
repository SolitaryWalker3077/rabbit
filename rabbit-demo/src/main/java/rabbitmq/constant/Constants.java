package rabbitmq.constant;

public class Constants {

    public static final String HOST = "43.140.242.83";

    public static final int POST = 5672;

    public static final String USER_NAME = "admin";

    public static final String PASSWORD = "admin";

    public static final String VIRTUAL_HOST = "study_rabbit";

    //工作队列模式
    public static final String WORK_QUEUE = "work.queue";

    //发布订阅模式
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    public static final String FANOUT_QUEUE1 = "fanout.queue1";
    public static final String FANOUT_QUEUE2 = "fanout.queue2";

    //路由模式
    public static final String DIRECT_EXCHANGE = "direct.exchange";
    public static final String DIRECT_QUEUE1 = "direct.queue1";
    public static final String DIRECT_QUEUE2 = "direct.queue2";

    //通配符模式
    public static final String TOPICS_EXCHANGE = "topics.exchange";
    public static final String TOPICS_QUEUE1 = "topics.queue1";
    public static final String TOPICS_QUEUE2 = "topics.queue2";

    //RPC模式
    public static final String RPC_REQUEST_QUEUE = "rpc.request.queue" ;
    public static final String RPC_RESPONSE_QUEUE = "rpc.response.queue";

    //Publisher Confirms(发布确认模式)
    public static final String PUBLISHER_CONFIRMS_QUEUE1 = "publisher.confirms.queue1";
    public static final String PUBLISHER_CONFIRMS_QUEUE2 = "publisher.confirms.queue2";
    public static final String PUBLISHER_CONFIRMS_QUEUE3 = "publisher.confirms.queue3";
}
