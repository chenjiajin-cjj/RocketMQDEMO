package chenjiajin.rocketmqdemo.study.genericity;

/**
 * 定义泛型类
 * @param <T>
 */
public class ObjectTool<T> {

    private T obj;

    private Integer integer;

    @Override
    public String toString() {
        return "ObjectTool{" +
                "obj=" + obj +
                ", integer=" + integer +
                '}';
    }

    public T getObj() {
        return obj;
    }

    public void setObj(final T obj) {
        this.obj = obj;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(final Integer integer) {
        this.integer = integer;
    }
}

/**
 * 该类创建的时候就要指定类型
 */
class test{
    public static void main(String[] args) {
        ObjectTool<String> objectTool = new ObjectTool<>();
        objectTool.setObj("aaa");
        objectTool.setInteger(111);
        System.out.println(objectTool);

        ObjectTool<Integer> objectTool1 = new ObjectTool<>();
        objectTool1.setObj(333);
        objectTool1.setInteger(444);
        System.out.println(objectTool1);
    }
}