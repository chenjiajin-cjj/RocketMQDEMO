package chenjiajin.rocketmqdemo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟一个订单实体类
 */
public class ProductOrder implements Serializable {
    //订单id
    private long orderId;
    //订单类型
    private String type;

    public ProductOrder() {

    }

    public ProductOrder(long orderId, String type) {
        this.orderId = orderId;
        this.type = type;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 模拟用户创建的订单信息
     * @return
     */
    public static List<ProductOrder> getorderList() {

        List<ProductOrder> list = new ArrayList<>();
        list.add(new ProductOrder(111L, "创建订单"));
        list.add(new ProductOrder(222L, "创建订单"));
        list.add(new ProductOrder(111L, "支付订单"));
        list.add(new ProductOrder(222L, "支付订单"));
        list.add(new ProductOrder(111L, "完成订单"));
        list.add(new ProductOrder(333L, "创建订单"));
        list.add(new ProductOrder(222L, "完成订单"));
        list.add(new ProductOrder(333L, "支付订单"));
        list.add(new ProductOrder(333L, "完成订单"));

        return list;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "orderId=" + orderId +
                ", type='" + type + '\'' +
                '}';
    }
}
