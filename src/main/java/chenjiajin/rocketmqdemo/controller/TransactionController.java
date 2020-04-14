package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.jms.TransactionProducen;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionProducen transactionProducen;

    @GetMapping("/send_msg")
    public Object callBack(String tag, String otherParam) throws Exception {
        Message message = new Message(jmsConfig.TOPIC, tag, tag + "", tag.getBytes());

        SendResult sendResult = transactionProducen.getProducer().
                sendMessageInTransaction(message, otherParam);

        System.out.printf("发送结果=%s, msg=%s \n", sendResult.getSendStatus(), sendResult.toString());

        return "send transaction producer ok";
    }
}
