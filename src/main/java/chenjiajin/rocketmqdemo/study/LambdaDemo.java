package chenjiajin.rocketmqdemo.study;

import java.util.ArrayList;
import java.util.List;

public class LambdaDemo {

    /**
     * // 1. 不需要参数,返回值为 5
     * () -> 5
     * <p>
     * // 2. 接收一个参数(数字类型),返回其2倍的值
     * x -> 2 * x
     * <p>
     * // 3. 接受2个参数(数字),并返回他们的差值
     * (x, y) -> x – y
     * <p>
     * // 4. 接收2个int型整数,返回他们的和
     * (int x, int y) -> x + y
     * <p>
     * // 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
     * (String s) -> System.out.print(s)
     */

    public static void main(String[] args) {

    }

    //循环的
    public static void lambdaList() {
        List<String> list = new ArrayList<>();
        int a = 0;
        while (true) {
            if (a == 100) {
                break;
            }
            list.add(a + "");
            a++;
        }
        list.forEach((player) -> System.out.print(player + "; "));
    }

    //线程的
    public static void lambdaRun() {

        // 1.1使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
                System.out.println("ddd");
            }
        }).start();

        // 1.2使用 lambda expression
        Thread thread = new Thread(() ->{
            System.out.println("Hello world !");
            System.out.println("Hello world !");
            System.out.println("Hello world !");
        });
        thread.start();
    }

}
