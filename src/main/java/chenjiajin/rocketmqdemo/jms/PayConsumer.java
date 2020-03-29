package chenjiajin.rocketmqdemo.jms;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
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
        //定义消费的主题 好像这个和生产的主题一样就行了   * 代表不过滤任何 tags
        consumer.subscribe(jmsConfig.TOPIC, "*");
        //定义消费的主题 好像这个和生产的主题一样就行了   tag： taga order   代表只有taga和order的才可以进来消费
//        consumer.subscribe(jmsConfig.TOPIC, " tage || order || testtt");
        //默认是集群模式，有且只有一个节点才会消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //广播模式，所有节点都会进行消费  运用场景，比如群聊之类的鬼东西
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.println("size:"+msgs.size());
            //即使msg一次性发了很多过来，但是每次还是一条条消费，所以就取了get(0)
            MessageExt msg = msgs.get(0);
            //获取消息重复消费次数
            int times = msg.getReconsumeTimes();
            System.out.println();
            System.out.println("测试搭建消费mq重试次数=" + times);
            try {
                System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), new String(msgs.get(0).getBody()));
                String topic = msg.getTopic();
                String body = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                String keys = msg.getKeys();
                System.out.println("测试消费：topic=" + topic + ", tags=" + tags + ", keys=" + keys + ", msg=" + body);
                //消息发送成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (UnsupportedEncodingException e) {
                System.out.println("测试消费异常");
                //如果重试2次不成功，则记录，人工介入
                if (times >= 2) {
                    System.out.println("测试的重试次数大于2，记录数据库，发短信通知开发人员或者运营人员");
                    //TODO 记录数据库，发短信通知开发人员或者运营人员
                    //告诉broker，消息成功 等于我不发了 浪费资源艾玛
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //消息发送失败
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
        System.out.println("test consumer start .....");
    }
}