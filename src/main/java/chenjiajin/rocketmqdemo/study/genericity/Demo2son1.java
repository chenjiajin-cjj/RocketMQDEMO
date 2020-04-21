package chenjiajin.rocketmqdemo.study.genericity;

//子类明确反省类的时候定义类型
public class Demo2son1 implements Demo2<String> {

    @Override
    public void show(final String s) {
        System.out.println(s);
    }
}
