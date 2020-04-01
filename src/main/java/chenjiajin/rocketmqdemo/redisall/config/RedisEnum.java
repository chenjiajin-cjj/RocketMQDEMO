//package chenjiajin.rocketmqdemo.redisall.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <p>
// * redis 配置常量
// * </p>
// * ***********************************************
// * BECAUSE OF CHOICE, STICK TO IT.               *
// * ***********************************************
// *
// * @author 陈嘉劲
// * @version V1.0
// * @date 2020年4月1日 15:19:08
// */
//public enum RedisEnum {
//
//    /**
//     * 单个redis 连接地址   119.23.109.20
//     */
//    HOST("127.0.0.1", "host"),
//
//    /**
//     * 密码
//     */
//    PASSWORD(null, "password"),
//
//    /**
//     * 端口号，单个使用
//     */
//    PORT(6379, "port"),
//
//    /**
//     * 连接超时时间（毫秒）
//     */
//    TIMEOUT(1000, "timeout"),
//
//    /**
//     * 最大连接数
//     */
//    MAX_TOTAL(100, "maxTotal"),
//
//    /**
//     * 连接池中的最小空闲连接
//     */
//    MAX_IDLE(20, "maxIdle"),
//
//    /**
//     * 连接池最大阻塞等待时间（使用负值表示没有限制）
//     */
//    MAX_WAIT(-1L, "maxWait"),
//
//    /**
//     * 集群地址设置 redis.cluster
//     */
//    CLUSTER_NODES("127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384", "clusterNodes"),
//
//    /**
//     *
//     */
//    COMMAND_TIMEOUT(500000, "commandTimeout");
//
//
//    private Object value;
//
//    private String key;
//
//    RedisEnum(Object index, String key) {
//        this.value = index;
//        this.key = key;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public Object getValue() {
//        return value;
//    }
//
//
//    /**
//     * 根据枚举名称得到key
//     * <p>
//     * 比如根据 "COMMAND_TIMEOUT" 得到 commandTimeout
//     *
//     * @param name
//     * @return
//     */
//    public static String getKeyByName(String name) {
//        RedisEnum[] redisEnums = RedisEnum.values();
//        for (RedisEnum redisEnumModel : redisEnums) {
//            if (redisEnumModel.name().equals(name)) {
//                return redisEnumModel.getKey();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据枚举名称得到key
//     * <p>
//     * 比如根据 "COMMAND_TIMEOUT" 得到 commandTimeout
//     *
//     * @param name
//     * @return
//     */
//    public static <T> T getVauleByName(String name) {
//        RedisEnum[] redisEnums = RedisEnum.values();
//        for (RedisEnum redisEnumModel : redisEnums) {
//            if (redisEnumModel.name().equals(name)) {
//                return (T) redisEnumModel.getValue();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据属性key获取属性value:
//     * <p>
//     * 比如根据 "commandTimeout" 得到 500000
//     *
//     * @param key
//     * @return
//     */
//    public static <T> T getValueByKey(String key) {
//        for (RedisEnum redisEnum : RedisEnum.values()) {
//            String k = redisEnum.getKey();
//            if (k.equals(key)) {
//                return (T) redisEnum.getValue();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据属性value获取属性key
//     * <p>
//     * 比如根据 500000 得到 "commandTimeout"
//     *
//     * @param value
//     * @return
//     */
//    public static String getKeyByValue(Object value) {
//        for (RedisEnum redisEnum : RedisEnum.values()) {
//            Object in = redisEnum.getValue();
//            if (in == value) {
//                return redisEnum.getKey();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 根据属性index获取对应的枚举值
//     * <p>
//     * 比如根据 500000 得到枚举值 "COMMAND_TIMEOUT"
//     *
//     * @param value
//     * @return
//     */
//    public static RedisEnum getTypeByValue(Object value) {
//        for (RedisEnum redisEnum : RedisEnum.values()) {
//            if (redisEnum.getValue() == value) {
//                return redisEnum;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 返回一个由key和vaule组成的map集合
//     *
//     * @return
//     */
//    public static Map<String, Object> getIndexKeyMap() {
//        Map<String, Object> map = new HashMap<>();
//        for (RedisEnum redisEnum : RedisEnum.values()) {
//            Object value = redisEnum.getValue();
//            String key = redisEnum.getKey();
//            map.put(key, value);
//        }
//        return map;
//    }
//
//
//}
