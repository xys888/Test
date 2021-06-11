package com.example.demo.controller;

import com.example.demo.config.RedisClient;
import com.example.demo.service.DayBalancePushService;
import com.example.demo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @date 2021年04月08日11:28
 */
@RestController
@RequestMapping("/day")
public class DayBalancePushTest {

    @Autowired
    private DayBalancePushService dayBalancePushService;
    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String test() throws Exception {
        //生成token
        String token = UUID.randomUUID().toString();
        boolean timeouttrans = redisClient.exists("timeouttrans");
        System.out.println(timeouttrans);
        if (!timeouttrans) {
            //把用户信息写入redis
            dayBalancePushService.update();
            redisClient.set("timeouttrans", token);
            redisClient.expire("timeouttrans", 600);
            System.out.println("key值已经失效请重试");
        } else {

        }
        return "ok";
    }


}
