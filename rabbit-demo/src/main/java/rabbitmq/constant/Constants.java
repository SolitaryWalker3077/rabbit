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

}
