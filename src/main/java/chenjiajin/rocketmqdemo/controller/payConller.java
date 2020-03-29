package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.jms.PayProducer;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 模拟测试一些投递数据
 */

@RestController
@RequestMapping("/study_mq")
public class payConller {

    //注入mq的配置
    @Autowired
    private PayProducer payProducer;


    /**
     * 发送同步消息，不带key
     *
     * @param json
     * @return
     * @throws Exception
     */
    @GetMapping("/synchronous/send_no_key")
    public Object synchronousSendNoKey(String json) throws Exception {
        /*
            创建一个mq的消息
            按顺序来
            1.topic 是生产者的名称，好像可以自动创建，但是windows不会配置
                所以只能去控制台创建一个一模一样名称的生产者了
            2.tag：taga是标签的意思 具体什么用现在还不清楚
            3.key：设置消费的唯一id
            4.body：往队列里面塞的信息 好像要byte的才行
         */
        Message message = new Message(jmsConfig.TOPIC, "taga", ("hello world rocketmq = " + json).getBytes());

        /*
            设置消息投递的延时级别   0 是不延时
            总共是18个等级
            1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            消息延时等级 分别是1秒-30秒  1分钟-30分钟 1-2小时
            执行等级的顺序是数过去的
            例如：5s是 2   30m是 16
         */
        message.setDelayTimeLevel(1);

        /*
            创建消息完毕之后，把消息发送出去
            send如果入参是单值   是同步发送 一收到就发出去，如果发送失败会进行重试
            同步发送有返回值
         */
//        SendResult sendResult = payProducer.getProducer().send(message);

        /**
         * 也可以发送多条消息
         * 虽然是一次性发了三条出去，但是在消费者端是分别三次消费而不是一次性消费
         */
        List<Message> msg = new ArrayList<>();
        msg.add(new Message(jmsConfig.TOPIC, "tag1", ("hello world rocketmq1 = " + json).getBytes()));
        msg.add(new Message(jmsConfig.TOPIC, "tag2", ("hello world rocketmq2 = " + json).getBytes()));
        msg.add(new Message(jmsConfig.TOPIC, "tag3", ("hello world rocketmq3 = " + json).getBytes()));
        SendResult sendResult = payProducer.getProducer().send(msg);

        System.out.printf("发送结果=%s, msg=%s \n", sendResult.getSendStatus(), sendResult.toString());

        return "send synchronous no key ok";
    }

    /**
     * 同步发送消息 有加key
     *
     * @param json
     * @return
     * @throws Exception
     */
    @GetMapping("/synchronous/send_have_key")
    public Object synchronousSendHaveKey(String json) throws Exception {
        //创建唯一的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //往队列里赛消息
        Message message = new Message(jmsConfig.TOPIC, "test", uuid, ("hello world rocketmq = " + json).getBytes());

        //设置延时等级1是1s
        message.setDelayTimeLevel(1);

        //同步发送直接发
        SendResult sendResult = payProducer.getProducer().send(message);

        System.out.printf("发送结果=%s, msg=%s", sendResult.getSendStatus(), sendResult.toString());
        return "send synchronous have key ok";
    }

    /**
     * 异步发送 有加key
     *
     * @param json
     * @return
     * @throws Exception
     */
    @GetMapping("/asynchronization/send_have_key")
    public Object asynchronizationSend(String json) throws Exception {
        //设置唯一的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //往队列里赛消息
        Message message = new Message(jmsConfig.TOPIC, "testtt", uuid, ("hello world rocketmq = " + json).getBytes());

        //设置发送的延迟时间 1是1s
        message.setDelayTimeLevel(1);
        //异步发送
        payProducer.getProducer().send(message, new SendCallback() {
            //然后直接获得返回结果
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("发送结果=%s, msg=%s", sendResult.getSendStatus(), sendResult.toString());
            }

            //补偿机制  也就是说如果异步发送失败了 就走这里 看是要重发还是怎样
            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步发送失败了艾玛");
            }
        });

        return "send asynchronization have key ok";
    }

    /**
     * 同步发送选择队列发送
     *
     * @param json
     * @return
     * @throws Exception
     */
    @GetMapping("/synchronous/send_check_queue")
    public Object synchronousQuene(String json) throws Exception {
        //创建唯一的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //往队列里赛消息
        Message message = new Message(jmsConfig.TOPIC, "", uuid, ("hello world rocketmq = " + json).getBytes());

        //设置延时等级1是1s
        message.setDelayTimeLevel(1);

        //同步发送选择队列发送
        SendResult sendResult = payProducer.getProducer().send(message, new MessageQueueSelector() {
            /**
             *
             * @param mqs 当前主题的所拥有的队列
             * @param msg 发送消息
             * @param arg 指定发送的队列
             * @return
             */
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                int queneNum = Integer.parseInt(arg.toString());
                System.out.println("同步发送选择队列：" + queneNum);
                return mqs.get(queneNum);
            }
        }, 2);

        System.out.printf("发送结果=%s, msg=%s", sendResult.getSendStatus(), sendResult.toString());
        return "send synchronous quene hava key ok";
    }

    /**
     * 异步发送选择队列发送
     *
     * @param json
     * @return
     * @throws Exception
     */
    @GetMapping("/asynchronization/send_check_queue")
    public Object asynchronizationQuene(String json) throws Exception {
        //创建唯一的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //往队列里赛消息
        Message message = new Message(jmsConfig.TOPIC, "tage", uuid, ("hello world rocketmq = " + json).getBytes());

        //设置延时等级1是1s
//        message.setDelayTimeLevel(1);

        //异步选择队列进行发送
        payProducer.getProducer().send(message, new MessageQueueSelector() {
            /**
             *
             * @param mqs 当前主题的所拥有的队列
             * @param msg 发送消息
             * @param arg 指定发送的队列
             * @return
             */
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                int queneNum = Integer.parseInt(arg.toString());
                System.out.println("异步选择队列发送:" + queneNum);
                return mqs.get(queneNum);
            }
        }, 3, new SendCallback() {
            //发送成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("发送结果=%s, msg=%s", sendResult.getSendStatus(), sendResult.toString());
            }

            //发送失败的回调
            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步发送选择队列失败了艾玛");
            }
        });

        return "send asynchronization quene hava key ok";
    }


}