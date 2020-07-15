package com.ioc.datasupport.component;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lsw
 */
@Component
public class RedisComponent {

    /*
     * V2版本目标：
     * 1、key的命名： 业务:模块:ID标识
     * 2、关于失效时间，增加一定的随机时间，防止同一时间出现大面积的回收
     * 3、灵活使用各种数据格式，不能只用string，特别是大数据量的时候
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*----基础string类型操作----*/

    /**
     * 将基本类型写入缓存：value值对象（stringRedisTemplate）
     * 基本类型：String、Integer、Double、Float、Short、Long、Boolean（将统一转化为string）
     * 使用stringRedisTemplate存入
     *
     * @param key     key
     * @param value   基础对象
     * @param seconds 失效间：秒
     */
    public void setStr(String key, String value, long seconds) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        // 设置值
        stringRedisTemplate.opsForValue().set(key, value);
        // 失效时间
        setStrExpire(key, seconds);
    }

    /**
     * 取得缓存（字符串类型）
     */
    public String getStr(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 删除缓存（stringRedisTemplate）
     * 根据key精确匹配删除
     *
     * @param key key
     */
    @SuppressWarnings("unchecked")
    public void delStr(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                stringRedisTemplate.delete(key[0]);
            } else {
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 删除缓存
     * 根据key精确匹配删除
     *
     * @param key key
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key    key
     * @param retain 是否保留
     * @return       string
     */
    public String getStr(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            // 不保留，删除
            stringRedisTemplate.delete(key);
        }

        return value;
    }

    /*----JSON字符串操作----*/

    /**
     * 将value对象以JSON格式写入缓存
     *
     * @param seconds 失效时间(秒)
     */
    public void setJson(String key, Object value, long seconds) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        setStrExpire(key, seconds);
    }

    /**
     * 获取缓存json对象<br>
     *
     * @param key   key
     * @param clazz 类型
     */
    public <T> T getJson(String key, Class<T> clazz) {
        return JSON.parseObject(stringRedisTemplate.boundValueOps(key).get(), clazz);
    }

    /**
     * 更新keyJSON对象field的值
     *
     * @param key   缓存key
     * @param field 缓存对象field
     * @param value 缓存对象field值
     */
    public void setJsonField(String key, String field, String value) {
        String jsonStr = stringRedisTemplate.boundValueOps(key).get();
        // 如果此时缓存失效，那parseObject应该就报错
        JSONObject obj = JSON.parseObject(jsonStr);
        obj.put(field, value);
        stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
    }

    /*---- redisTemplate处理对象----*/

    /**
     * 获取缓存
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     */
    public Object getObj(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param retain 是否保留
     */
    public Object getObj(String key, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /*----map操作：opsForHash----*/

    /**
     * 将map写入缓存
     *
     * @param seconds 失效时间(秒)
     */
    public <T> void setMap(String key, Map<String, T> map, long seconds) {
        redisTemplate.opsForHash().putAll(key, map);
        setExpire(key, seconds);
    }

    /**
     * 向key对应的map中添加缓存对象
     */
    public <T> void addMap(String key, Map<String, T> map, long seconds) {
        redisTemplate.opsForHash().putAll(key, map);
        setExpire(key, seconds);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取map缓存
     */
    public <T> Map<String, T> mget(String key) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 获取map缓存中的某条记录
     */
    public <T> T mgetMapField(String key, String field) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param fieldSet map中该对象的属性
     */
    public void delMapField(String key, Set<String> fieldSet) {
        if (CollectionUtils.isEmpty(fieldSet)) {
            return;
        }
        Object[] arr = new Object[fieldSet.size()];

        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(arr);
    }

    /*----操作set类型：boundSetOps----*/

    /**
     * 添加set
     */
    public void sadd(String key, Set<?> set, long seconds) {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        Object[] arr = new Object[set.size()];
        set.toArray(arr);

        redisTemplate.boundSetOps(key).add(arr);
        setExpire(key, seconds);
    }

    public Set<?> sget(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    /**
     * 删除set集合中的对象
     */
    public void srem(String key, Set<?> set) {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        Object[] arr = new Object[set.size()];
        set.toArray(arr);

        redisTemplate.boundSetOps(key).remove(arr);
    }

    /**
     * set重命名
     */
    public void srename(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /*----失效时间----*/

    /**
     * 指定缓存的失效时间
     *
     * @param key     缓存KEY
     * @param seconds 失效时间(秒)
     */
    public void setStrExpire(String key, long seconds) {
        if (seconds > 0) {
            // 转为毫秒，添加随机毫秒，防止大面积数据回收，造成阻塞
            long millisecond = seconds * 1000 + RandomUtil.randomLong(0, 64);
            stringRedisTemplate.expire(key, millisecond, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key     缓存KEY
     * @param seconds 失效时间(秒)
     */
    public void setExpire(String key, long seconds) {
        if (seconds > 0) {
            // 转为毫秒，添加随机毫秒，防止大面积数据回收，造成阻塞
            long millisecond = seconds * 1000 + RandomUtil.randomLong(0, 64);
            redisTemplate.expire(key, millisecond, TimeUnit.MILLISECONDS);
        }
    }

    /*----保留功能----*/

    /**
     * 递减操作
     */
    public double decr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     */
    public double incr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    /*-------不允许使用的方法-------*/

    /**
     * （批量删除）
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     */
    @Deprecated
    public void fuzzyDel(String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 模糊删除（批量删除）
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     */
    @Deprecated
    public void fuzzyDelStr(String... pattern) {
        for (String kp : pattern) {
            stringRedisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 模糊查询keys
     */
    @Deprecated
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
