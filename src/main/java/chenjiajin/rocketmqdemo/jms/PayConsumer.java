package chenjiajin.rocketmqdemo.jms;

import chenjiajin.rocketmqdemo.controller.payConller;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 消费者
 */
@Component
public class PayConsumer {

    private DefaultMQPushConsumer consumer;

    //所属的组 干嘛用我也不知道
    private String consumerGroup = "pay_consumer_group";

    public PayConsumer() throws MQClientException {
        //配置消费者组
        consumer = new DefaultMQPushConsumer(consumerGroup);
        //配置消费的地址
        consumer.setNamesrvAddr(jmsConfig.NAME_SERVER_ADDR);
        //设置消费方式，从最后一个开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //定义消费的主题
        consumer.subscribe(jmsConfig.TOPIC, "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt msg = msgs.get(0);
            //获取消息重复消费次数
            int times = msg.getReconsumeTimes();
            System.out.println("重试次数=" + times);
            try {
                System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), new String(msgs.get(0).getBody()));
                String topic = msg.getTopic();
                String body = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                String keys = msg.getKeys();
                System.out.println("topic=" + topic + ", tags=" + tags + ", keys=" + keys + ", msg=" + body);
                //消息发送成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (UnsupportedEncodingException e) {
                System.out.println("消费异常");
                //如果重试2次不成功，则记录，人工介入
                if (times >= 2) {
                    System.out.println("重试次数大于2，记录数据库，发短信通知开发人员或者运营人员");
                    //TODO 记录数据库，发短信通知开发人员或者运营人员
                    //告诉broker，消息成功 等于我不发了 浪费资源艾玛
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //消息发送失败
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
        System.out.println("consumer start .....");
    }

}
