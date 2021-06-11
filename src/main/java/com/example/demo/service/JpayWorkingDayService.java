package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.config.RedisClient;
import com.example.demo.dao.JpayWorkingDayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JpayWorkingDayService {
    @Autowired
    private JpayWorkingDayMapper jpayWorkingDayMapper;
    @Autowired
    private RedisClient redisClient;

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
    public void dealAmsCompsntasklockStatus() {
        jpayWorkingDayMapper.dealAmsCompsntasklockStatus();

    }


    public Map<String, String> get(String timess) {
        //定义空对象
        String holidayinfoList = null;
        try {
            //从缓存中拿到值
            holidayinfoList = redisClient.get("holidayList");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //定义存放对象的集合List
        List<JpayWorkingDayResInfo> newHolidayList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        //将字符串转换成JSON格式的字符串
        JSONArray infos = JSONArray.parseArray(holidayinfoList);
        for (int i = 0; i < infos.size(); i++) {
            //遍历Json格式的数组
            //并将其单个数组转换成Java对象
            JSONObject jpayHolidayinfo = JSONObject.parseObject(infos.get(i).toString());
            JpayWorkingDayResInfo jpayHoliday = (JpayWorkingDayResInfo) JSONObject.toJavaObject(jpayHolidayinfo, JpayWorkingDayResInfo.class);
            //将对象存到List集合中
            newHolidayList.add(jpayHoliday);
        }
        Date workTime = null;
        //格式化时间格式
        if (isExistence(timess, newHolidayList)) {
            workTime = getWorkTime(timess, newHolidayList);
        } else if (isMonday(timess)) {
            try {
                Calendar instance = Calendar.getInstance();
                instance.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timess));
                instance.add(Calendar.DATE, +3);
                workTime = instance.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> map1 = new HashMap<>();
        map.put("holidayTime", new SimpleDateFormat("yyyy-MM-dd").format(workTime));
        map.put("size", String.valueOf(newHolidayList.size()));
        map.put("isExistence", String.valueOf(isExistence(timess, newHolidayList)));
        return map;
    }

    public Date getWorkTime(String timeStr, List<JpayWorkingDayResInfo> newHolidayList) {
        //判断日期是否是周六周日
        Calendar calendar = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //定义一个空时间对象
        Date parse = null;
        try {
            //String类型的时间类型转换成Date类型
            parse = simpleDateFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar = Calendar.getInstance();
        //date类型转换成日历类型
        calendar.setTime(parse);
        //遍历循环比较值
        for (int i = newHolidayList.size() - 1; i > 0; i--) {
            Date whDate = newHolidayList.get(i).getWhDate();
            //获取日历的实例
            Calendar calendarDate = Calendar.getInstance();
            //将date类型转换成Calendar
            calendarDate.setTime(whDate);
            calendar.setTime(getHoliday(calendar, calendarDate, "WORKINGDAY"));
            parse = getWeeKDay(calendar, "WORKINGDAY");
        }
        return parse;
    }


    public void transClearFile(){
        jpayWorkingDayMapper.selectRechargeByDate("2021-03-01","2021-03-04","2021-03-01","2021-03-02");
    }
    /**
     * 判断当前是否是周六或者周日
     * 如果是的话向前推一天
     *
     * @param calendar
     * @return
     */
    private Date getWeeKDay(Calendar calendar, String whStatus) {
        //传过来日历时间
        //比较当前的天数是否是周六或者周日
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            //如果是其中的一天则时间往前推一天 return 结束if语句
            calendar.add(Calendar.DATE, +1);
        } else {
            //如果比较之后两天都不是周六周日
            //返回当前时间
            return calendar.getTime();
        }
        return getWeeKDay(calendar, whStatus);
    }

    /**
     * 判断当前时间是否是节假日
     * 如果是的话向前推一天
     *
     * @param calendar
     * @param calendarDate
     * @return
     */
    private Date getHoliday(Calendar calendar, Calendar calendarDate, String whStatus) {
        //获取到表中日期并且与得到的时间进行比较
        //先比较年 紧接着比较 月 和 日
        if (calendar.get(Calendar.YEAR) == calendarDate.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == calendarDate.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == calendarDate.get(Calendar.DAY_OF_MONTH)) {
            //如果是同年同月同日 则向前推一 结束if循环 return 结果
            calendar.add(Calendar.DATE, -1);
        } else {
            return calendar.getTime();
        }
        return getHoliday(calendar, calendarDate, whStatus);
    }

    /**
     * 判断当前时间是否是列表中的时间
     *
     * @param timess
     * @param newHolidayList
     * @return
     */
    private static Boolean isExistence(String timess, List<JpayWorkingDayResInfo> newHolidayList) {
        //迭代出从缓存中拿出来的集合
        //判断当前时间是否是缓存中的存褚时间
        List<String> list = new ArrayList<>();
        for (int i = 0; i < newHolidayList.size(); i++) {
            Date whDate = newHolidayList.get(i).getWhDate();
            list.add(new SimpleDateFormat("yyyy-MM-dd").format(whDate));
        }
        if (list.contains(timess)) {
            return true;
        }
        return false;
    }

    private static Boolean isMonday(String timess) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(timess);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return true;
        }
        return false;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getCss() {
        System.out.println("缓存1:::" + new SimpleDateFormat("HH:mm:ss:sss").format(new Date()));
    }
}
