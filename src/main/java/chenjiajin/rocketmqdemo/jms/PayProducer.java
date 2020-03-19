package chenjiajin.rocketmqdemo.jms;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

@Component
public class PayProducer {
    private String producerGroup = "pay_producer_group"; //所属的组 干嘛用我也不知道
    private DefaultMQProducer producer;

    public PayProducer() {
        //设置单节点
        producer = new DefaultMQProducer(producerGroup);

        //生产者投递消息重试次数 3次  但是有可能会重复投递  就要在投递的时候设置key了
        producer.setRetryTimesWhenSendAsyncFailed(3);

        //指定NameServer地址，多个地址以 ; 隔开
        //如producer.setNamesrvAddr(127.0.0.1:9876;192.168.1.1:8081);
        producer.setNamesrvAddr(jmsConfig.NAME_SERVER_ADDR);
        start();
    }

    //构造函数
    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器进行关闭
     */
    public void shutDown() {
        this.producer.shutdown();
    }
}
