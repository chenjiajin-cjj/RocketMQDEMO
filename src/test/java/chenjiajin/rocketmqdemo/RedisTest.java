package chenjiajin.rocketmqdemo;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import chenjiajin.rocketmqdemo.study.Annotationdemo.MyAnnotation;
import lombok.val;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisTest {

    @Test
    public void add() {
        List<OrerInfo> orerInfos = new ArrayList<>();
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("sdffdfd");
        orerInfos.add(orerInfo);
        List<OrerInfo> oo = test(orerInfos);
        System.out.println(oo);
    }

    public <T> T test(T t) {
        return t;
    }

    /**
     * lambda 写线程
     * @throws Exception
     */
    @Test
    public void ppasd() throws Exception{
        Thread thread = new Thread(() -> {
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());
            System.out.println("通过Runnable实现多线程，名称：" + Thread.currentThread().getName());

        });
        thread.setName("sdsdsdsdsdsd");
        thread.start();
        Thread.sleep(2000);
        System.out.println("99999");
    }

}
