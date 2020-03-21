package chenjiajin.rocketmqdemo.dbtest.service.impl;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import chenjiajin.rocketmqdemo.dbtest.mapper.OrerInfoMapper;
import chenjiajin.rocketmqdemo.dbtest.service.IOrerInfoService;
import chenjiajin.rocketmqdemo.jms.PayProducer;
import chenjiajin.rocketmqdemo.jms.jmsConfig;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenjiajin@Jowim.com
 * @since 2020-03-21
 */
@Service
public class OrerInfoServiceImpl extends ServiceImpl<OrerInfoMapper, OrerInfo> implements IOrerInfoService {

    public static final String TOPIC = "order_db_topic";

    //注入mq的配置
    @Autowired
    private PayProducer payProducer;

    @Override
    public Object addToOrder(String json) {

        OrerInfo orerInfo = JSONObject.parseObject(json, OrerInfo.class);
        //设置唯一的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //往队列里赛消息
        Message message = new Message(TOPIC, "", uuid, json.getBytes());

        //设置发送的延迟时间 1是1s
        message.setDelayTimeLevel(0);
        int a = 0;
        //异步发送
        try {
            payProducer.getProducer().send(message, new SendCallback() {
                //然后直接获得返回结果
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("发送成功");
                }

                //补偿机制  也就是说如果异步发送失败了 就走这里 看是要重发还是怎样
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败");
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ooo";

    }
}
