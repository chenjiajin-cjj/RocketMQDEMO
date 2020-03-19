package chenjiajin.rocketmqdemo.jms;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

@Component
public class PayProducer {

    private String producerGroup = "pay_group";

    private String nameServerAddr = "127.0.0.1:9876";

    private DefaultMQProducer producer;

    public PayProducer(){
        //设置单节点
        producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        //如producer.setNamesrvAddr(127.0.0.1:9876;192.168.1.1:8081);
        producer.setNamesrvAddr(nameServerAddr);
        start();
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start(){
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
