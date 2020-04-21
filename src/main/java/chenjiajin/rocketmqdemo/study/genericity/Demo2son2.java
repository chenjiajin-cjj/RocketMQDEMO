package chenjiajin.rocketmqdemo.study.genericity;

//子类不明确反省类的时候没有定义类型
public class Demo2son2<T> implements Demo2<T> {

    @Override
    public void show(final T t) {
        System.out.println(t);
    }

    public static void main(String[] args) {
        Demo2<String> demo2 = new Demo2son1();
        demo2.show("demo2son1");
        Demo2<Integer> demo21 = new Demo2son2<>();
        demo21.show(6);
    }
}
