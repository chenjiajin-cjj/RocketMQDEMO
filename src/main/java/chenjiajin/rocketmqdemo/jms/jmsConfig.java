package chenjiajin.rocketmqdemo.jms;

public class jmsConfig {

    //这里写mq的地址，我写线下的，可以换成线上
    public static final String NAME_SERVER_ADDR = "127.0.0.1:9876";

    //设置消费主题
    public static final String TOPIC = "xdclass_pay_test_topic";

}
