package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.domain.ProductOrder;
import chenjiajin.rocketmqdemo.jms.PayProducer;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模拟测试一些发送订单数据
 */

@RestController
@RequestMapping("/order")
public class OrderController {
    //注入mq的配置
    @Autowired
    private PayProducer payProducer;

    /**
     * 模拟批量投递订单消息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/create_order")
    public Object createOrder() throws Exception {

        List<ProductOrder> list = ProductOrder.getorderList();

        for (int i = 0; i < list.size(); i++) {
            ProductOrder order = list.get(i);
            //创建一条消息
            Message message = new Message(jmsConfig.ORDER_TOPIC, "", order.getOrderId() + "", order.toString().getBytes());
            //同步指定队列发送
            SendResult sendResult = payProducer.getProducer().send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long id = (Long) o;
                    long index = id % list.size();
                    return list.get((int) index);
                }
            }, order.getOrderId());
            System.out.printf("订单发送结果=%s, sendResult=%s ,orderid=%s,orderType=%s\n", sendResult.getSendStatus(), sendResult.toString(),order.getOrderId(),order.getType());
        }

        return null;
    }
}
