package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.jms.PayProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class payConller {

    //注入mq的配置
    @Autowired
    private PayProducer payProducer;

    private static final String topic = "xdclass_pay_test_topic";

    @GetMapping("/api/v1/pay_cb")
    public Object callBack(String text) throws Exception {
        /*
            创建一个mq的消息
            按顺序来
            1.topic 是生产者的名称，好像可以自动创建，但是windows不会配置
                所以只能去控制台创建一个一模一样名称的生产者了
            2.tag：taga是标签的意思 具体什么用现在还不清楚
            3.body：往队列里面塞的信息
         */
        Message message = new Message(topic, "taga", ("hello xdclass rocketmq = " + text).getBytes());
        /*
            创建消息完毕之后，吧消息发送出去
         */
        SendResult sendResult = payProducer.getProducer().send(message);
        /*
            打印一下结果看看
         */
        System.out.println(sendResult);
        return null;
    }

}
