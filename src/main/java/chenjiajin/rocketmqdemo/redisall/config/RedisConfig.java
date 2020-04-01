//package chenjiajin.rocketmqdemo.redisall.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.net.UnknownHostException;
//
///**
// * <p>
// * redis 自定义启动
// * </p>
// * ***********************************************
// * BECAUSE OF CHOICE, STICK TO IT.               *
// * ***********************************************
// *
// * @author 陈嘉劲
// * @version V1.0
// * @date 2020年4月1日 15:19:03
// */
//@Configuration
//@ConditionalOnClass({JedisCluster.class})
//@Slf4j
//public class RedisConfig {
//
//    /**
//     * 日志打印
//     */
//
//
//    /**
//     * 连接池配置信息
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        //最大连接数
//        jedisPoolConfig.setMaxTotal((Integer) RedisEnum.MAX_TOTAL.getValue());
//        //最小空闲连接数
//        jedisPoolConfig.setMaxIdle((Integer) RedisEnum.MAX_IDLE.getValue());
//        //当池内没有可用连接时，最大等待时间
//        jedisPoolConfig.setMaxWaitMillis(10000);
//
//        jedisPoolConfig.setMaxWaitMillis((Long) RedisEnum.MAX_WAIT.getValue());
//        //其他属性可以自行添加
//        return jedisPoolConfig;
//    }
//
//    /**
//     * 单例
//     *
//     * @return JedisPool
//     */
//    @Bean
//    public JedisPool redisPoolFactory(JedisPoolConfig jedisPoolConfig) {
//        log.info(">>>>>>>>>>> 开启redis缓存机制...");
//        return new JedisPool(jedisPoolConfig, RedisEnum.HOST.getValue().toString(), (Integer) RedisEnum.PORT.getValue(), (Integer) RedisEnum.TIMEOUT.getValue(), null);
//    }
//
//
//    /**
//     * 设置数据存入 redis 的序列化方式 </br>
//     * redisTemplate 序列化默认使用的jdkSerializeable, 存储二进制字节码, 导致key会出现乱码，所以自定义 序列化类
//     *
//     * @param redisConnectionFactory redis连接工厂
//     */
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
//            throws UnknownHostException {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * jedis连接工厂
//     *
//     * @param jedisPoolConfig jedisPoolConfig
//     * @return redis连接工厂
//     */
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        //设置redis服务器的host或者ip地址
//        redisStandaloneConfiguration.setHostName(RedisEnum.HOST.getValue().toString());
//        redisStandaloneConfiguration.setPort((Integer) RedisEnum.PORT.getValue());
//        //获得默认的连接池构造
//        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
//        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        //修改我们的连接池配置
//        jpcf.poolConfig(jedisPoolConfig);
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }
//
//
//}