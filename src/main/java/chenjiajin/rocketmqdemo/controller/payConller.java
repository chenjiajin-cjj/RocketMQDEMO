package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.jms.PayProducer;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class payConller {

    //注入mq的配置
    @Autowired
    private PayProducer payProducer;


    @GetMapping("/api/v1/pay_cb")
    public Object callBack(String text) throws Exception {
        /*
            创建一个mq的消息
            按顺序来
            1.topic 是生产者的名称，好像可以自动创建，但是windows不会配置
                所以只能去控制台创建一个一模一样名称的生产者了
            2.tag：taga是标签的意思 具体什么用现在还不清楚
            3.key：设置消费的唯一id
            4.body：往队列里面塞的信息 好像要byte的才行
         */
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //这个是没有加key的
//        Message message = new Message(jmsConfig.TOPIC, "taga", ("hello xdclass rocketmq = " + text).getBytes());

        //设置唯一的uuid 保证消息不会被重复投递   有加key
        Message message = new Message(jmsConfig.TOPIC, "taga", uuid, ("hello xdclass rocketmq = " + text).getBytes());

        /*
            message.setDelayTimeLevel(2);
            设置消息投递的延时级别   0 是不延时
            总共是18个等级
            1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            消息延时等级 分别是1秒-30秒  1分钟-30分钟 1-2小时
            执行等级的顺序是数过去的
            例如：5s是 2   30m是 16
         */
        message.setDelayTimeLevel(1);

        /*
            1

            创建消息完毕之后，把消息发送出去
            send如果入参是单值   是同步发送 一收到就发出去，如果发送失败会进行重试
            同步发送有返回值
         */
//        SendResult sendResult = payProducer.getProducer().send(message);

        /*

            2

            异步发送 比同步发送的入参 多了个 new SendCallBack
            异步发送没有返回值
         */
        payProducer.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("发送结果=%s, msg=%s", sendResult.getSendStatus(), sendResult.toString());
            }

            /**
             * 补偿机制  也就是说如果异步发送失败了 就走这里 看是要重发还是怎样
             */
            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步发送失败了艾玛");
            }
        });

        return "ok";
    }

}
