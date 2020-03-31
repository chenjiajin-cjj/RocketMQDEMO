package chenjiajin.rocketmqdemo.dbtest.mapper;

import chenjiajin.rocketmqdemo.dbtest.entity.OrerInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjiajin@Jowim.com
 * @since 2020-03-21
 */
public interface OrerInfoMapper extends BaseMapper<OrerInfo> {

    @Insert("INSERT INTO `rrrmq`.`orer_info`(`order_number_id`, `order_name`, `order_create`, `order_type`, `order_product_id`, `order_user_id`) VALUES (#{orderNumberId}, #{orderName}, #{orderCreate}, #{orderType}, #{orderProductId}, #{orderUserId})")
    Integer addOrder (@Param("orderNumberId")String orderNumberId,
                      @Param("orderName")String orderName,
                      @Param("orderCreate")String orderCreate,
                      @Param("orderType")String orderType,
                      @Param("orderProductId")String orderProductId,
                      @Param("orderUserId")String orderUserId);

}
