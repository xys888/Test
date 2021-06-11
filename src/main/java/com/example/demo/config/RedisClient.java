package com.example.demo.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisClient<T> {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String get(String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public void setobj(String key, T value) throws Exception {
        Jedis jedis = null;
        try {
            Set<T> set = new HashSet<T>();
            set.add(value);
            jedis = jedisPool.getResource();
            jedis.sadd(key, String.valueOf(set));
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    public boolean exists1(final String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * 判断key是否过期
     *
     * @param key
     * @return
     */
    public boolean isExpire(String key) {
        return expire(key) > 1 ? false : true;
    }


    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     *
     * @param key
     * @return
     */
    public long expire(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

    /**
     * 设置失效时间方法
     *
     * @param key
     * @param seconds
     * @return
     * @throws Exception
     */
    public void expire(String key, int seconds) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis != null) {
                jedis.expire(key, seconds);
            }
        } catch (Exception e) {
            throw new Exception("redis expire异常【" + e.getMessage() + "】");
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}