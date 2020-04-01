package chenjiajin.rocketmqdemo.redisall.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * redis 操作接口层 序列化
 * </p>
 * ***********************************************
 * BECAUSE OF CHOICE, STICK TO IT.               *
 * ***********************************************
 *
 * @author 陈嘉劲
 * @version V1.0
 * @date 2020年4月1日 15:18:46
 */
public interface IRedisCache {
    /**
     * 为{@code key}设置{@code value}。可覆盖
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    boolean set(String key, Object value) throws Exception;

    /**
     * 为{@code key}设置{@code value}和{@code seconds}中的到期时间。(以秒为单位)
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     * @throws Exception
     */
    boolean set(String key, Object value, final long seconds) throws Exception;

    /**
     * 将{@code key}的{@code value}和过期时间设置为{@code milliseconds}。(以毫秒为单位)
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     * @throws Exception
     */
    boolean pSetEx(String key, Object value, final long milliseconds) throws Exception;

    /**
     * 只在{@code key}不存在的情况下，才为{@code key}设置{@code value}。不覆盖
     *
     * @param key
     * @param obj
     * @return
     * @throws Exception
     */
    boolean setNX(String key, Object value) throws Exception;

    /**
     * 使用{@code tuple}中提供的键-值对将多个键设置为多个值。可覆盖
     *
     * @param tuple
     * @return
     * @throws Exception
     */
    boolean mSet(Map<String, Object> tuple) throws Exception;

    /**
     * 使用{@code tuple}中提供的键-值对将多个键设置为多个值，只有在提供所有的key都不存在时才使用，只要有一个key存在就会全部失败。
     *
     * @param tuple
     * @return
     * @throws Exception
     */
    boolean mSetNX(Map<String, Object> tuple) throws Exception;

    /**
     * 用给定的{@code值}覆盖从指定的{@code offset}开始的{@code key}部分。
     * <p>
     * 用指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     *
     * @param key
     * @param value
     * @param seconds
     * @throws Exception
     */
    void setRange(String key, Object value, long seconds) throws Exception;

    /**
     * 将存储为{@code key}字符串值的整数值+1。如果key不存在则初始化值为0，并且+1。
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long incr(String key) throws Exception;

    /**
     * 通过{@code value}增加{@code key}存储的整数值。如果key不存在则初始化值为0，在执行该方法（+ value）。
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long incr(String key, long value) throws Exception;

    /**
     * 通过{@code delta}增加{@code key}的浮点数值。如果key不存在则初始化值为0，在执行该方法（+ value）。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Double incr(String key, double value) throws Exception;

    /**
     * 将存储为{@code key}字符串值的整数值-1。如果key不存在则初始化值为0，并且-1。
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long decr(String key) throws Exception;

    /**
     * 通过{@code value}减少{@code key}存储的整数值。如果key不存在则初始化值为0，在执行该方法（- value）。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long decr(String key, long value) throws Exception;

    /**
     * 将{@code值}附加到{@code key}。命令将 value 追加到 key 原来的值的末尾。
     * <p>
     * 追加指定值之后， key 中字符串的长度。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long append(String key, Object value) throws Exception;

    /**
     * 获取{@code key}的值。
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T get(String key) throws Exception;

    /**
     * 模糊查询{@code key}
     *
     * @param key
     * @param beforeOrDown 模糊前||模糊后
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> Set<T> get(String key, boolean beforeOrDown) throws Exception;

    /**
     * 获取{@code start}和{@code end}之间{@code key}的子字符串值。
     * <p>
     * 字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * <p>
     * start=0,end=-1 为获取全部
     *
     * @param key
     * @param start
     * @param end
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T get(String key, long start, long end) throws Exception;

    /**
     * 设置{@code key}的{@code value}，返回其旧值。
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T getSet(String key, Object value) throws Exception;

    /**
     * 获取多个{@code key}。值按请求键的顺序返回。
     *
     * @param key
     * @return
     * @throws Exception
     */
    List<Object> mGet(byte[]... key) throws Exception;

    /**
     * 获取存储在{@code key}上的值的长度。
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long getLen(String key) throws Exception;

    /**
     * 删除 指定{@code key}
     *
     * @param key
     * @return
     * @throws Exception
     */
    boolean del(String key) throws Exception;

    /**
     * 删除指定多个{@code key}
     *
     * @param key
     * @return
     * @throws Exception
     */
    boolean del(byte[]... key) throws Exception;

    /**
     * 模糊删除指定的模糊值{@code key}
     * {@code beforeOrDown} 模糊前||模糊后
     *
     * @param key
     * @param beforeOrDown
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> Set<T> del(String key, boolean beforeOrDown) throws Exception;

    /**
     * 为{@code key}值设置{@code values} 返回列表长度
     * <p>
     * Redis Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lRSet(String key, Object values) throws Exception;

    /**
     * 为{@code key}值设置{@code values} 返回列表长度
     * <p>
     * Redis Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lRSet(String key, byte[]... values) throws Exception;

    /**
     * 为{@code key}值设置{@code values} 返回列表长度
     * <p>
     * Redis Lpush 命令用于将一个或多个值插入到列表的头部(最左边)。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lLSet(String key, Object values) throws Exception;

    /**
     * 为{@code key}值设置{@code values} 返回列表长度
     * <p>
     * Redis Lpush 命令用于将一个或多个值插入到列表的头部(最左边)。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lLSet(String key, byte[]... values) throws Exception;

    /**
     * 只在列表存在时才将{@code值}附加到{@code key}。
     * <p>
     * Redis Rpushx 命令用于将一个值插入到已存在的列表尾部(最右边)。如果列表不存在，操作无效。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lRSetX(String key, Object value) throws Exception;

    /**
     * 只在列表存在时才将{@code值}附加到{@code key}。
     * <p>
     * Redis Lpushx 命令用于将一个值插入到已存在的列表头部(最左边)。如果列表不存在，操作无效。
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    Long lLSetX(String key, Object value) throws Exception;

    /**
     * 插入{@code value} {@link Position#BEFORE}或{@link Position#AFTER}现有{@code pivot} {@code key}。
     * <p>
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
     * <p>
     * Redis Linsert 命令用于在列表的元素前或者后插入元素。当指定元素不存在于列表中时，不执行任何操作。
     * 当列表不存在时，被视为空列表，不执行任何操作。
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param key
     * @param flag  true:BEFORE>>> pivot 之前   false:AFTER>>> pivot 之后
     * @param pivot
     * @param value
     * @return
     * @throws Exception
     */
    Long lSet(String key, boolean flag, String pivot, String value) throws Exception;

    /**
     * 将{@code值}列表元素设置为{@code index}。 替换
     * <p>
     * Redis Lset 通过索引来设置元素的值。
     * 当索引参数超出范围，或对一个空列表进行 LSET 时，返回一个错误。
     *
     * @param key
     * @param index 下标值
     * @param value
     * @throws Exception
     */
    void lSet(String key, long index, String value) throws Exception;

    /**
     * 获取存储在{@code key}的列表的大小。
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long lLen(String key) throws Exception;

    /**
     * 从{@code key}列表中获取{@code start}和{@code end}之间的元素。
     * <p>
     * Redis Lrange 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。
     * 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> List<T> lGet(String key, long start, long end) throws Exception;

    /**
     * 在{@code key}的{@code index}表单列表中获取元素。
     * <p>
     * Redis Lindex 命令用于通过索引获取列表中的元素。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T lGet(String key, long index) throws Exception;

    /**
     * 修剪列表在{@code key}到{@code start}和{@code end}之间的元素。
     * <p>
     * Redis Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @throws Exception
     */
    void lTrim(String key, long start, long end) throws Exception;

    /**
     * 从存储在{@code key}的列表中删除{@code value}出现的第一个{@code count}。
     * <p>
     * Redis Lrem 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
     * COUNT 的值可以是以下几种：
     * *count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     * *count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     * *count = 0 : 移除表中所有与 VALUE 相等的值。
     * <p>
     * 返回值 ： 被移除元素的数量。 列表不存在时返回 0 。
     *
     * @param key
     * @param count
     * @param value
     * @return
     * @throws Exception
     */
    Long lRem(String key, long count, String value) throws Exception;

    /**
     * 删除并返回存储在{@code key}中的列表中的第一个元素。
     * <p>
     * Redis Lpop 命令用于移除并返回列表的第一个元素。
     * 列表的第一个元素。 当列表 key 不存在时，返回 nil 。
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T lLPop(String key) throws Exception;

    /**
     * 删除并返回存储在{@code key}的列表中的最后一个元素。
     * <p>
     * Redis Rpop 命令用于移除并返回列表的最后一个元素。
     * 列表的最后一个元素。 当列表不存在时，返回 nil 。
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T lRPop(String key) throws Exception;

    /**
     * 从{@code key}的列表中删除最后一个元素，将其附加到{@code keys}并返回其值。
     * <p>
     * Redis Rpoplpush 命令用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
     * 返回：被弹出的元素。
     *
     * @param key
     * @param keys
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T lRPopSet(String key, String keys) throws Exception;
}
