package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.jms.PayProducer;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
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
            3.body：往队列里面塞的信息 好像要byte的才行
         */
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
//        Message message = new Message(jmsConfig.TOPIC, "taga", ("hello xdclass rocketmq = " + text).getBytes());
       //设置唯一的uuid 保证消息不会被重复投递
        Message message = new Message(jmsConfig.TOPIC, "taga", uuid,("hello xdclass rocketmq = " + text).getBytes());

        /*
            创建消息完毕之后，把消息发送出去
         */
        SendResult sendResult = payProducer.getProducer().send(message);
        /*
            打印一下结果看看
         */
        System.out.println(sendResult);
        return null;
    }

}
