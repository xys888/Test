package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.dao.JpayWorkingDayMapper;
import com.example.demo.service.JpayWorkingDayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_1 {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private JpayWorkingDayService jpayWorkingDayService;

    @Test
    public void set() {
     redisTemplate.opsForValue().set("myKey","myValue");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

    @Test
    public void set1() {
        List<JpayWorkingDayResInfo> holidayList = jpayWorkingDayService.selectWorkDay();
        //获取到redis实例
        //存入数据
        redisTemplate.opsForValue().set("holidayList", JSON.toJSON(holidayList).toString());
    }

    @Test
    public void get(){
        Object holidayList1 = redisTemplate.opsForValue().get("holidayList");
        List<JpayWorkingDayResInfo> newHolidayList = new ArrayList<>();
//      ObjectMapper objectMapper = new ObjectMapper();
//      JpayWorkingDayResInfo jpayWorkingDayResInfo = objectMapper.convertValue(holidayList1, JpayWorkingDayResInfo.class);
//      System.out.println(jpayWorkingDayResInfo.toString());
        JSONArray objects = JSONArray.parseArray(holidayList1.toString());
        for (int i = 0; i < objects.size(); i++) {
            Object o = objects.get(i);
            JSONObject jsonObject2 = JSONObject.parseObject(o.toString());
            JpayWorkingDayResInfo jpayHoliday = (JpayWorkingDayResInfo) JSONObject.toJavaObject(jsonObject2, JpayWorkingDayResInfo.class);
            newHolidayList.add(jpayHoliday);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format(newHolidayList.get(18).getWhDate()));
        System.out.println(newHolidayList.get(18).getUpdateUser());
        System.out.println(newHolidayList.get(18).getUpdateUser());
        System.out.println(newHolidayList.size());
    }

}