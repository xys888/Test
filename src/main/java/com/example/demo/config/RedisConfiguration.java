package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 读取配置
 */
@Configuration
public class RedisConfiguration {

    @Bean(name= "jedis.pool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                @Value("${jedis.pool.host}")String host,
                @Value("${jedis.pool.port}")int port,
                @Value("${jedis.pool.database}")int database,
                               @Value("${jedis.pool.password}")String password) {
//        System.out.println(database);
//        System.out.println(host);
//        System.out.println(port);
//        System.out.println(password);
//        System.out.println(config);
        return new JedisPool(config, host, port,database);
    }

    @Bean(name= "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig (@Value("${jedis.pool.config.maxTotal}")int maxTotal,
                                @Value("${jedis.pool.config.maxIdle}")int maxIdle,
                                @Value("${jedis.pool.config.maxWaitMillis}")int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

    @Bean
    public RestTemplate restTemplate() {
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);// 设置超时
        requestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

}