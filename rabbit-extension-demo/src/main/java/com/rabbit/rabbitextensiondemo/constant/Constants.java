package com.rabbit.rabbitextensiondemo.constant;

public class Constants {

    public static final String ACK_QUEUE = "ack.queue";
    public static final String ACK_EXCHANGE = "ack.exchange";

    public static final String PRES_QUEUE = "pres.queue";
    public static final String PRES_EXCHANGE = "pres.exchange";

    //发送方确认
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";

    //重试机制
    public static final String RETRY_QUEUE = "retry.queue";
    public static final String RETRY_EXCHANGE = "retry.exchange";

    //ttl
    public static final String TTL_QUEUE = "ttl.queue";
    public static final String TTL_QUEUE2 = "ttl2.queue";
    public static final String TTL_EXCHANGE = "ttl.exchange";

    //死信
    public static final String NORMAL_QUEUE = "normal.queue";
    public static final String NORMAL_EXCHANGE = "normal.exchange";

    public static final String DL_QUEUE = "dl.queue";
    public static final String DL_EXCHANGE= "dl.exchange";

}
