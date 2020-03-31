package chenjiajin.rocketmqdemo.dbtest.jms;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import chenjiajin.rocketmqdemo.dbtest.mapper.OrerInfoMapper;
import chenjiajin.rocketmqdemo.dbtest.service.impl.OrerInfoServiceImpl;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Component
@RunWith(SpringRunner.class) //非mvc模式下使用autowired注入不报空的注解 2个
@SpringBootTest
public class OrderConsumer {

    private DefaultMQPushConsumer consumer;

    @Autowired
    private OrerInfoMapper orerInfoMapper;

    //所属的组 干嘛用我也不知道
    private String consumerGroup = "order_consumer";

    public OrderConsumer() throws MQClientException {
        //配置消费者组
        consumer = new DefaultMQPushConsumer(consumerGroup);
        //配置消费的地址
        consumer.setNamesrvAddr(jmsConfig.NAME_SERVER_ADDR);
        //设置消费方式，从最后一个开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //默认是集群模式，可以更改为广播，但是广播方式不支持重试
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        //定义消费的主题 好像这个和生产的主题一样就行了
        consumer.subscribe(OrerInfoServiceImpl.TOPIC, "*");
        //MessageListenerOrderly 代表我要顺序消费
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                try {
                    MessageExt msg = list.get(0);
                    OrerInfo orerInfo = JSONObject.parseObject(msg.getBody(), OrerInfo.class);
                    int res = orerInfoMapper.addOrder("55", orerInfo.getOrderName(), orerInfo.getOrderCreate(), orerInfo.getOrderType(), orerInfo.getOrderProductId(), orerInfo.getOrderUserId());
                    if (res > 0) {
                        System.out.printf("订单消费完成：%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
                        return ConsumeOrderlyStatus.SUCCESS;
                    } else {
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }
        });
        consumer.start();
        System.out.println("payOrder consumer start .....");
    }
}
