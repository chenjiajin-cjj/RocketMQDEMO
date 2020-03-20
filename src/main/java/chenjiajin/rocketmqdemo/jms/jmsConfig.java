package chenjiajin.rocketmqdemo.jms;

public class jmsConfig {

    //这里写mq的地址，我写线下的，可以换成线上
    public static final String NAME_SERVER_ADDR = "127.0.0.1:9876";

    /**
     * 设置消费主题
     * 如果没有在控制台手动创建的话，会自动创建
     * 自动创建的主题的队列数默认为4
     * payConller测试用的topic
     */
    public static final String TOPIC = "cjj_pay_test_topic";

    //订单的topic
    public static final String ORDER_TOPIC = "cjj_pay_order_topic";

}
