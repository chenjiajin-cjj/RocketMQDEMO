package chenjiajin.rocketmqdemo.dbtest.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenjiajin@Jowim.com
 * @since 2020-03-21
 */
@TableName("orer_info")
public class OrerInfo extends Model<OrerInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    /**
     * 订单编号
     */
    @TableField("order_number_id")
    private String orderNumberId;
    /**
     * 订单名称
     */
    @TableField("order_name")
    private String orderName;
    /**
     * 创建时间
     */
    @TableField("order_create")
    private String orderCreate;
    /**
     * 订单类别
     */
    @TableField("order_type")
    private String orderType;
    /**
     * 对应的商品id
     */
    @TableField("order_product_id")
    private String orderProductId;
    /**
     * 用户id
     */
    @TableField("order_user_id")
    private String orderUserId;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumberId() {
        return orderNumberId;
    }

    public void setOrderNumberId(String orderNumberId) {
        this.orderNumberId = orderNumberId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCreate() {
        return orderCreate;
    }

    public void setOrderCreate(String orderCreate) {
        this.orderCreate = orderCreate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

    @Override
    public String toString() {
        return "OrerInfo{" +
        ", orderId=" + orderId +
        ", orderNumberId=" + orderNumberId +
        ", orderName=" + orderName +
        ", orderCreate=" + orderCreate +
        ", orderType=" + orderType +
        ", orderProductId=" + orderProductId +
        ", orderUserId=" + orderUserId +
        "}";
    }
}
