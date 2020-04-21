package chenjiajin.rocketmqdemo.study.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通配符的使用
 */
public class Demo4 {

    public static void main(String[] args) {
        //List集合装载的是Integer，可以调用该方法
        List<Integer> integer = new ArrayList<>();
        test(integer);
        //List集合装载的是String，在编译时期就报错了
        List<String> strings = new ArrayList<>();
        //test(strings); //String不是number的子类 所以报错

        List<pet> pets = new ArrayList<>();
        test1(pets);
        List<dog> dogs = new ArrayList<>();
        test1(dogs);
        List<zoo> zoos = new ArrayList<>();
        test1(zoos); //zoo是dog父类的父类，所以也能传
        List<person> persons = new ArrayList<>();
        //test1(persons); //person不是dog的父类  所以报错
    }

    /**
     * 只能装载number的子类或者自身，所以不能传除此以外的类型进来
     * @param list
     */
    public static void test(List<? extends Number> list) {

    }

    /**
     * 只能装载dog或者他的父类的父类的父类......
     * @param list
     */
    public static void test1(List<? super dog> list) {

    }

}
class zoo{

}
class pet extends zoo{

}
class dog extends pet{

}
class person{

}
