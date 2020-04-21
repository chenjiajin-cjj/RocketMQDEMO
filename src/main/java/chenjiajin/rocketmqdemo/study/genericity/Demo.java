package chenjiajin.rocketmqdemo.study.genericity;

/**
 * 泛型测试类
 */
public class Demo {

    //定义泛型方法..
    public static <T> String show(T t) {
        System.out.println(t);
        return "4";
    }

    public static void main(String[] args) {
        System.out.println(show("fff"));
        System.out.println(show(11));
        System.out.println(show(1.55));
    }
}
