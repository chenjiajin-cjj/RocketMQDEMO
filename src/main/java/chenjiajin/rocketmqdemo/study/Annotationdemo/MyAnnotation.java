package chenjiajin.rocketmqdemo.study.Annotationdemo;

import io.netty.buffer.DefaultByteBufHolder;

/**
 * 注意：在注解上定义的成员变量只能是String、数组、Class、枚举类、注解
 */
public @interface MyAnnotation {
    String name();
    int age();
}
