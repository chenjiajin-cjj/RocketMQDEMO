package chenjiajin.rocketmqdemo.controller;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import chenjiajin.rocketmqdemo.redisall.service.IRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private IRedisCache iRedisCache;


    @GetMapping("string")
    public Object stringRedis() throws Exception {
        boolean result = iRedisCache.set("tt","我是天才啊哈哈哈哈",10);
        if(result){
            return iRedisCache.get("tt");
        }
        return "失败了";
    }

    @GetMapping("object")
    public Object objectRedis() throws Exception {
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        boolean result = iRedisCache.set("object",orerInfo);
        if(result){
            return iRedisCache.get("object");
        }
        return "失败了";
    }

    @GetMapping("list")
    public Object listRedis() throws Exception {
        List<OrerInfo> list = new ArrayList<>();
        OrerInfo orerInfo = new OrerInfo();
        orerInfo.setOrderName("我是天才啊啊哈哈哈哈哈啊");
        list.add(orerInfo);
        orerInfo = new OrerInfo();
        orerInfo.setOrderName("陈嘉劲666666");
        list.add(orerInfo);
        boolean result = iRedisCache.set("list",list);
        if(result){
            return iRedisCache.get("list");
        }
        return "失败了";
    }
}
