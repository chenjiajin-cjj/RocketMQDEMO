package chenjiajin.rocketmqdemo.jms;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 消费者
 */
@Component
public class PayOrderlyConsumer {

    private DefaultMQPushConsumer consumer;

    //所属的组 干嘛用我也不知道
    private String consumerGroup = "pay_orderly_consumer_group";

    public PayOrderlyConsumer() throws MQClientException {
        //配置消费者组
        consumer = new DefaultMQPushConsumer(consumerGroup);
        //配置消费的地址
        consumer.setNamesrvAddr(jmsConfig.NAME_SERVER_ADDR);
        //设置消费方式，从最后一个开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //默认是集群模式，可以更改为广播，但是广播方式不支持重试
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        //定义消费的主题 好像这个和生产的主题一样就行了
        consumer.subscribe(jmsConfig.ORDER_TOPIC, "*");
        //MessageListenerOrderly 代表我要顺序消费























        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                try {
                    MessageExt msg = list.get(0);

                    System.out.printf("订单消费完成：%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(msg.getBody()));
                    //TODO 业务逻辑操作
                    return ConsumeOrderlyStatus.SUCCESS;
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