package chenjiajin.rocketmqdemo.dbtest.service;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenjiajin@Jowim.com
 * @since 2020-03-21
 */
public interface IOrerInfoService extends IService<OrerInfo> {

    Object addToOrder(String json);
}
