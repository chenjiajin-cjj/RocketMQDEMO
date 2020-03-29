package chenjiajin.rocketmqdemo.jms;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class TransactionProducen {
    //所属的组 干嘛用我也不知道
    private String producerGroup = "transaction_producer_group";

    //事务监听器
    private TransactionListener transactionListener = new TransactionListenerImpl();

    private TransactionMQProducer producer = null;

    /**
     * 自定义线程池 定义的时候要给线程加个名称
     */
    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(200), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        }
    });


    public TransactionProducen() {

        producer = new TransactionMQProducer(producerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        //如producer.setNamesrvAddr(127.0.0.1:9876;192.168.1.1:8081);
        producer.setNamesrvAddr(jmsConfig.NAME_SERVER_ADDR);

        producer.setTransactionListener(transactionListener);

        producer.setExecutorService(executorService);
        start();
    }

    //构造函数
    public TransactionMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文，使用上下文监听器进行关闭
     */
    public void shutDown() {
        this.producer.shutdown();
    }
}

class TransactionListenerImpl implements TransactionListener {
    /**
     * 执行本地事务
     *
     * @param message
     * @param o       传入的otherParam
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {

        System.out.println("进入执行本地事务");
        String body = new String(message.getBody());    //获取信息
        String key = message.getKeys();   //获取key
        String transactionId = message.getTransactionId();   //获取事务的id
        System.out.println("transactionId=" + transactionId + ",key=" + key + ",body=" + body);
        //伪代码  操作数据库  执行本地事务   开始~~
        int status = Integer.parseInt(o.toString());
        //二次确认消息 然后消费者可以消费
        if (status == 1) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        //回滚消息，broker端会删除半消息
        if (status == 2) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        //broker端 会回查消息  再或者什么都不响应
        if (status == 3) {
            return LocalTransactionState.UNKNOW;
        }
        //伪代码  操作数据库  执行本地事务   结束~~


        return null;
    }

    /**
     * 回查消息，要么commit要么rollback  reconsumeTimes不生效
     * 只有在处理 半消息发送 UNKNOW 的时候才会触发回查消息
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("进入回查消息");
        String body = new String(messageExt.getBody());    //获取信息
        String key = messageExt.getKeys();   //获取key
        String transactionId = messageExt.getTransactionId();   //获取事务的id
        System.out.println("transactionId=" + transactionId + ",key=" + key + ",body=" + body);
        //要么commit  要么rollback
//        return LocalTransactionState.COMMIT_MESSAGE;
//        return LocalTransactionState.ROLLBACK_MESSAGE;
//        return LocalTransactionState.UNKNOW;
        //可以根据key 去检查本地事务消息是否完成
        return null;
    }
}