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
}
