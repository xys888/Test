package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.config.RedisClient;
import com.example.demo.dao.JpayWorkingDayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JpayWorkingDayService1 {
    @Autowired
    private JpayWorkingDayMapper jpayWorkingDayMapper;
    @Autowired
    private RedisClient redisClient;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    private final static Integer FORWORD = -1;
//    private final static Integer BEFORE = 1;
    public List<JpayWorkingDayResInfo> selectWorkDay() {
        return jpayWorkingDayMapper.selectWorkDay();
    }

    public void set1() {
        List<JpayWorkingDayResInfo> holidayList = jpayWorkingDayMapper.selectWorkDay();
        //获取到redis实例
        //存入数据
        try {
            redisClient.set("holidayList", JSON.toJSON(holidayList).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getWorkingDayTime(String timess,Integer args) {
        //从缓存中拿到值
        String holidayinfoList = redisClient.get("holidayList");
        //定义存放假期时间对象的map
        Map<String, String> map = new HashMap<String, String>();
        //将字符串转换成JSON格式的数组
        JSONArray infos = JSONArray.parseArray(holidayinfoList);
        for (int i = 0; i < infos.size(); i++) {
            //遍历Json格式的数组
            //并将其单个数组转换成Java对象
            JSONObject jpayHolidayinfo = JSONObject.parseObject(infos.get(i).toString());
            if ("HOLIDAY".equals(jpayHolidayinfo.get("whStatus"))) {
                map.put(simpleDateFormat.format(jpayHolidayinfo.get("whDate")), simpleDateFormat.format(jpayHolidayinfo.get("whDate")));
            }
        }
        map.put("whDate", simpleDateFormat.format(workingDayTime(timess, map,args)));
        return map;
    }

    /**
     * 传入时间和map集合
     * 得出下一个工作日的时间并返回
     *
     * @param timeStr
     * @param map
     * @return
     */
    public Date workingDayTime(String timeStr, Map<String, String> map,Integer args) {
        //定义一个日历类型的时间
        Calendar calendar = null;
        //定义一个Date对象
        Date parse = null;
        try {
            //将String类型的时间类型转换成Date类型
            parse = simpleDateFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //得到日历对象的实例
        calendar = Calendar.getInstance();
        //date类型转换成日历类型
        calendar.setTime(parse);
        //判断传入的时间是否是假期列表中的时间
        //如果是的话先排除掉列表中的时间
        //如果不是的话将当前时间加一 得出下一个工作日时间
        if (map.containsValue(simpleDateFormat.format(parse))) {
            parse = isExistence(calendar, map, args);
        } else {

            calendar.add(Calendar.DATE, args);
            calendar.setTime(getWeeKDay(calendar, args));
            parse = isExistence(calendar, map,args);
        }
        return parse;
    }

    /**
     * 判断当前是否是周六或者周日
     * 如果是的话向后推一天
     * @param
     * @return
     */
    private static Date getWeeKDay(Calendar calendar,Integer args) {
        //传过来日历时间
        //比较当前的天数是否是周六或者周日
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            //如果是其中的一天则时间往前推一天 return 结束if语句
            calendar.add(Calendar.DATE, args);
        } else {
            //如果比较之后两天都不是周六周日
            //返回当前时间
            return calendar.getTime();
        }
        return getWeeKDay(calendar,args);
    }

    /**
     * 判断当前时间是否是节假日列表中的时间
     * 如果存在则时间+一天继续判断
     * @param calendar
     * @param map
     * @return
     */
    private static Date isExistence(Calendar calendar, Map<String, String> map,Integer args) {
        //迭代出从缓存中拿出来的集合
        Date time = calendar.getTime();
        if (map.containsValue(simpleDateFormat.format(time))) {
            calendar.add(Calendar.DATE, args);
        } else {
            return calendar.getTime();
        }
        //判断是否是周六周日
        Date weeKDay = getWeeKDay(calendar,args);
        calendar.setTime(weeKDay);
        return isExistence(calendar, map,args);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getCss() {
        System.out.println("缓存2:::" + new SimpleDateFormat("HH:mm:ss:sss").format(new Date()));
    }
}
