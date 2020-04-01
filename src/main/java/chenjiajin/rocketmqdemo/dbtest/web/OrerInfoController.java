package chenjiajin.rocketmqdemo.dbtest.web;


import chenjiajin.rocketmqdemo.dbtest.service.IOrerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author chenjiajin@Jowim.com
 * @since 2020-03-21
 */
@RestController
@RequestMapping("/jowim/orerInfo")
public class OrerInfoController {

    @Autowired
    private IOrerInfoService iOrerInfoService;

    @PostMapping("/add_order")
    public Object addToOrder(@RequestBody String json){
        return iOrerInfoService.addToOrder(json);
    }
}

