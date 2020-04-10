package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/redis")
//@Component
//@RunWith(SpringRunner.class) //非mvc模式下使用autowired注入不报空的注解 2个
//@SpringBootTest
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/string/string")
    public Object stringRedis() throws Exception {
        redisTemplate.opsForValue().set("tt", "val");
        return redisTemplate.opsForValue().get("tt");
    }

    @GetMapping("/string/object")
    public Object objectRedis() throws Exception {
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        redisTemplate.opsForValue().set("object", orerInfo);
        return redisTemplate.opsForValue().get("object");
    }

    @GetMapping("/string/list")
    public Object listRedis() throws Exception {
        List<OrerInfo> list = new ArrayList<>();
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        list.add(orerInfo);
        orerInfo = new OrerInfo();
        orerInfo.setOrderName("陈嘉劲666666");
        list.add(orerInfo);
        redisTemplate.opsForValue().set("list", list);
        return redisTemplate.opsForValue().get("list");
    }

    @GetMapping("/list/list")
    public Object listListRedis() throws Exception {
        List<OrerInfo> list = new ArrayList<>();
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        list.add(orerInfo);
        orerInfo = new OrerInfo();
        orerInfo.setOrderName("陈嘉劲666666");
        list.add(orerInfo);
        redisTemplate.opsForList().leftPush("listha", list);
        return redisTemplate.opsForList().range("listha",0,-1);
    }

    @GetMapping("/set/set")
    public Object setSetList() throws Exception {
        List<OrerInfo> list = new ArrayList<>();
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        list.add(orerInfo);
        orerInfo = new OrerInfo();
        orerInfo.setOrderName("陈嘉劲666666");
        list.add(orerInfo);
        redisTemplate.opsForSet().add("setsa","ppp","ssss","222","dsasd",list);


        return redisTemplate.opsForSet().members("setsa");
    }

}
