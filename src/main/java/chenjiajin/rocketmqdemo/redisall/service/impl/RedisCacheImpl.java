package chenjiajin.rocketmqdemo.redisall.service.impl;

import chenjiajin.rocketmqdemo.redisall.service.IRedisCache;
import chenjiajin.rocketmqdemo.redisall.utils.SerializerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * redis 操作实现层 序列化
 * </p>
 * ***********************************************
 * BECAUSE OF CHOICE, STICK TO IT.               *
 * ***********************************************
 *
 * @author 陈嘉劲
 * @version V1.0
 * @date 2020年4月1日 15:18:53
 */
@Component
public class RedisCacheImpl implements IRedisCache {

    /**
     * 日志打印
     */
    private final static Logger LOG = LogManager.getLogger(RedisCacheImpl.class);

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public boolean set(String key, Object value) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.set(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return flag;
    }

    @Override
    public boolean set(String key, Object value, final long seconds) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setEx(key.getBytes(), seconds, SerializerUtil.serialize(value));
            }
        });
        return flag;
    }

    @Override
    public boolean pSetEx(String key, Object value, final long milliseconds) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.pSetEx(key.getBytes(), milliseconds, SerializerUtil.serialize(value));
            }
        });
        return flag;
    }

    @Override
    public boolean setNX(String key, Object value) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return flag;
    }

    @Override
    public boolean mSet(Map<String, Object> tuple) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.mSet(SerializerUtil.serialize(tuple));
            }
        });
        return flag;
    }

    @Override
    public boolean mSetNX(Map<String, Object> tuple) throws Exception {
        boolean flag = false;
        flag = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.mSetNX(SerializerUtil.serialize(tuple));
            }
        });
        return flag;
    }

    @Override
    public void setRange(String key, Object value, long seconds) throws Exception {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setRange(key.getBytes(), SerializerUtil.serialize(value), seconds);
                return null;
            }
        });
    }

    @Override
    public Long incr(String key) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
        return result;
    }

    @Override
    public Long incr(String key, long value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(), value);
            }
        });
        return result;
    }

    @Override
    public Double incr(String key, double value) throws Exception {
        Double result = null;
        result = redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(), value);
            }
        });
        return result;
    }

    @Override
    public Long decr(String key) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decr(key.getBytes());
            }
        });
        return result;
    }

    @Override
    public Long decr(String key, long value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decrBy(key.getBytes(), value);
            }
        });
        return result;
    }

    @Override
    public Long append(String key, Object value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.append(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return result;
    }

    @Override
    public <T> T get(String key) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public <T> Set<T> get(String key, boolean beforeOrDown) throws Exception {
        Set<byte[]> result = null;
        try {
            String obj = "*";
            obj.getBytes();
            if (beforeOrDown) {
                key = obj + key;
            } else {
                key = key + obj;
            }
            final byte[] bkey = key.getBytes();
            result = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
                @Override
                public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.keys(bkey);
                }
            });
        } catch (Exception e) {
            LOG.error(e);
        }
        if (result == null || result.size() == 0)
            return null;
        Set<T> set = new HashSet<T>();
        for (byte[] bytes : result) {
            set.add((T) SerializerUtil.unserialize(bytes));
        }
        return set;
    }

    @Override
    public <T> T get(String key, long start, long end) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.getRange(key.getBytes(), start, end);
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public <T> T getSet(String key, Object value) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.getSet(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public List<Object> mGet(byte[]... keys) throws Exception {
        List<byte[]> result = null;
        result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.mGet(keys);
            }
        });
        return SerializerUtil.unserialize(result);
    }

    @Override
    public Long getLen(String key) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.strLen(key.getBytes());
            }
        });
        return result;
    }

    @Override
    public boolean del(String key) throws Exception {
        boolean result = false;
        try {
            final byte[] bkey = key.getBytes();
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(bkey);
                    return true;
                }
            });
        } catch (Exception e) {
            LOG.error(e);
        }
        return result;
    }

    @Override
    public boolean del(byte[]... key) throws Exception {
        boolean flag = false;
        try {
            flag = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(key);
                    return true;
                }
            });
        } catch (Exception e) {
            LOG.error(e);
        }
        return flag;
    }

    @Override
    public <T> Set<T> del(String key, boolean beforeOrDown) throws Exception {
        Set<byte[]> result = null;
        boolean flag = false;
        try {
            String obj = "*";
            obj.getBytes();
            if (beforeOrDown) {
                key = obj + key;
            } else {
                key = key + obj;
            }
            final byte[] bkey = key.getBytes();
            result = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
                @Override
                public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.keys(bkey);
                }
            });
            if (result != null && result.size() > 0) {
                for (byte[] bytes : result) {
                    flag = redisTemplate.execute(new RedisCallback<Boolean>() {
                        @Override
                        public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                            connection.del(bytes);
                            return true;
                        }
                    });
                }
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        if (result == null || result.size() == 0)
            return null;
        Set<T> set = new HashSet<T>();
        for (byte[] bytes : result) {
            set.add((T) SerializerUtil.unserialize(bytes));
        }
        return set;
    }

    @Override
    public Long lRSet(String key, Object values) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPush(key.getBytes(), SerializerUtil.serialize(values));
            }
        });
        return result;
    }

    @Override
    public Long lRSet(String key, byte[]... values) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPush(key.getBytes(), values);
            }
        });
        return result;
    }

    @Override
    public Long lLSet(String key, Object values) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lPush(key.getBytes(), SerializerUtil.serialize(values));
            }
        });
        return result;
    }

    @Override
    public Long lLSet(String key, byte[]... values) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lPush(key.getBytes(), values);
            }
        });
        return result;
    }

    @Override
    public Long lRSetX(String key, Object value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPushX(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return result;
    }

    @Override
    public Long lLSetX(String key, Object value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lPushX(key.getBytes(), SerializerUtil.serialize(value));
            }
        });
        return result;
    }

    @Override
    public Long lSet(String key, boolean flag, String pivot, String value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (flag) {
                    return connection.lInsert(key.getBytes(), RedisListCommands.Position.BEFORE, SerializerUtil.serialize(pivot), SerializerUtil.serialize(value));
                }
                return connection.lInsert(key.getBytes(), RedisListCommands.Position.AFTER, SerializerUtil.serialize(pivot), SerializerUtil.serialize(value));
            }
        });
        return result;
    }

    @Override
    public void lSet(String key, long index, String value) throws Exception {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.lSet(key.getBytes(), index, SerializerUtil.serialize(value));
                return true;
            }
        });
    }

    @Override
    public Long lLen(String key) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lLen(key.getBytes());
            }
        });
        return result;
    }

    @Override
    public <T> List<T> lGet(String key, long start, long end) throws Exception {
        List<byte[]> result = null;
        result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lRange(key.getBytes(), start, end);
            }
        });

        return (List<T>) SerializerUtil.unserialize(result);
    }

    @Override
    public <T> T lGet(String key, long index) throws Exception {
        byte[] result;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lIndex(key.getBytes(), index);
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public void lTrim(String key, long start, long end) throws Exception {
        boolean result;
        result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.lTrim(key.getBytes(), start, end);
                return true;
            }
        });
    }

    @Override
    public Long lRem(String key, long count, String value) throws Exception {
        Long result = null;
        result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lRem(key.getBytes(), count, SerializerUtil.serialize(value));
            }
        });
        return result;
    }

    @Override
    public <T> T lLPop(String key) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lPop(key.getBytes());
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public <T> T lRPop(String key) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPop(key.getBytes());
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

    @Override
    public <T> T lRPopSet(String key, String keys) throws Exception {
        byte[] result = null;
        result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPopLPush(key.getBytes(), SerializerUtil.serialize(keys));
            }
        });
        return (T) SerializerUtil.unserialize(result);
    }

}
