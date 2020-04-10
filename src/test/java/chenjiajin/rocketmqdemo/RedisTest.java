package chenjiajin.rocketmqdemo;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
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


}
