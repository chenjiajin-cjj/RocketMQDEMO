package chenjiajin.rocketmqdemo.study.genericity;

import javax.imageio.stream.IIOByteBuffer;

/**
 * 定义泛型接口
 * @param <T>
 */
public interface Demo2<T> {
    void show(T t);
}