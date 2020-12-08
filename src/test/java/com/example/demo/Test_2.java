package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.config.RedisClient;
import com.example.demo.dao.JpayWorkingDayMapper;
import com.example.demo.service.JpayWorkingDayService;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {"com.example.demo.dao"})
@SpringBootTest
public class Test_2 {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private JpayWorkingDayService jpayWorkingDayService;

    @Test
    public void set1() {
        List<JpayWorkingDayResInfo> holidayList = jpayWorkingDayService.selectWorkDay();
        //获取到redis实例
        //存入数据
        try {
            redisClient.set("holidayList", JSON.toJSON(holidayList).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get(){
        Object holidayList1 = null;
        try {
            holidayList1 = redisClient.get("holidayList");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Date workTime = getWorkTime("2020-10-19", newHolidayList);
        System.out.println(workTime);
        System.out.println(newHolidayList.size());
    }

    public Date getWorkTime(String timeStr, List<JpayWorkingDayResInfo> newHolidayList) {
        //判断日期是否是周六周日
        Calendar calendar=null;
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = simpleDateFormat1.parse(timeStr);
            calendar = Calendar.getInstance();
            calendar.setTime(parse);
            //DAY_OF_WEEK判断日期是本周的第几天
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, -1);
            }
            //判断日期是否是节假日
            //反序列化
            for (JpayWorkingDayResInfo ca : newHolidayList) {
                //获取到表中日期
                Date whDate = ca.getWhDate();
                //获取日历的实例
                Calendar calendarDate = Calendar.getInstance();
                //将date类型转换成Calendar
                calendarDate.setTime(whDate);
                if (calendar.get(Calendar.YEAR) == calendarDate.get(Calendar.YEAR) &&
                        calendar.get(Calendar.MONTH) == calendarDate.get(Calendar.MONTH) &&
                        calendar.get(Calendar.DAY_OF_MONTH) == calendarDate.get(Calendar.DAY_OF_MONTH)) {
                    calendar.add(Calendar.DATE, -1);
                } else {
                    return calendar.getTime();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

}