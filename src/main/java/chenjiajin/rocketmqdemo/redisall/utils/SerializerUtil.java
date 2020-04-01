package chenjiajin.rocketmqdemo.redisall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * <p>
 * 序列化工具
 * </p>
 * ***********************************************
 * BECAUSE OF CHOICE, STICK TO IT.               *
 * ***********************************************
 *
 * @author 陈嘉劲
 * @version V1.0
 * @date 2020年4月1日 15:18:37
 */
public class SerializerUtil {

    /**
     * 控制台打印输出
     */
    private final static Logger LOG = LoggerFactory.getLogger(SerializerUtil.class);

    /**
     * 序列号为字节码
     *
     * @param object 可以序列化的对象
     * @return byte[]
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            LOG.debug("序列化异常");
        }
        return null;
    }

    /**
     * 序列化map对象 Map<byte[], byte[]>
     *
     * @param hash Map<String,Object> 原始对象
     * @return
     */
    public static Map<byte[], byte[]> serialize(Map<String, Object> hash) {
        if (null == hash) {
            return null;
        }
        Iterator<String> it = hash.keySet().iterator();
        Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
        while (it.hasNext()) {
            String key = it.next();
            Object object = hash.get(key);
            map.put(key.getBytes(), SerializerUtil.serialize(object));
        }
        return map;
    }

    /**
     * String...  序列化为 byte[][]
     *
     * @param keys
     * @return
     */
    public static byte[][] serialize(String... keys) {
        byte[][] values = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            values[i] = keys[i].getBytes();
        }
        return values;
    }

    /**
     * Object...  序列化为 byte[][]
     *
     * @param keys
     * @return
     */
    public static byte[][] serialize(Object... keys) {
        byte[][] values = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            values[i] = SerializerUtil.serialize(keys[i]);
        }
        return values;
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            LOG.debug("byte[] 反序列化异常");
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     */
    public static List<Object> unserialize(List<byte[]> bytes) {
        ByteArrayInputStream bais = null;
        List<Object> objects = new ArrayList<>();
        try {
            for (byte[] bs : bytes) {
                bais = new ByteArrayInputStream(bs);
                ObjectInputStream ois = new ObjectInputStream(bais);
                objects.add(ois.readObject());
            }
            return objects;
        } catch (Exception e) {
            LOG.debug("List<byte[]> 反序列化异常");
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     */
    public static List<Object> unserialize(Set<byte[]> bytes) {
        ByteArrayInputStream bais = null;
        List<Object> objects = new ArrayList<>();
        try {
            for (byte[] bs : bytes) {
                bais = new ByteArrayInputStream(bs);
                ObjectInputStream ois = new ObjectInputStream(bais);
                objects.add(ois.readObject());
            }
            return objects;
        } catch (Exception e) {
            LOG.debug("Set<byte[]> 反序列化异常");
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param hash hash 对象
     * @return
     */
    public static Map<String, Object> unserialize(Map<byte[], byte[]> hash) {
        if (null == hash) {
            return null;
        }
        Iterator<byte[]> it = hash.keySet().iterator();
        Map<String, Object> map = new HashMap<>();
        while (it.hasNext()) {
            byte[] key = it.next();
            byte[] object = hash.get(key);
            map.put(new String(key), SerializerUtil.unserialize(object));
        }
        return map;
    }


}
