package chenjiajin.rocketmqdemo.study.Annotationdemo;

public class AnnotationTest {

    @MyAnnotation(name = "chenjiajin",age = 18)
    public static void test(String name,Integer age){
        System.out.println(name);
        System.out.println(age);
    }

    public static void main(String[] args) {
        test("",66);
    }

}
