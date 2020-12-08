package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    private static String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    //第一种线程池
    public static void test1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第一种线程池" + Thread.currentThread().getName() + "running...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //第二种线程池
    public static void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第二种线程池" + Thread.currentThread().getName() + "running...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //第三种线程池
    public static void test3() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟三秒");
            }
        }, 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟 1 秒后每三秒执行一次");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    //第四种线程池
    public static void test4() {
        ExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("第四种线程池" + Thread.currentThread().getName() + "running...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Date test5() {
        Calendar calendar = Calendar.getInstance();
        DateTime now = DateTime.now();
        Date date = now.toDate();
        System.out.println("------" + DateTime.now());
        System.out.println("------===" + date);
        System.out.println("++++++" + new Date());
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getWorkingDayTimeByD(String timess) {
        //定义一个日历类型的时间
        Calendar calendar = Calendar.getInstance();
        //定义一个Date对象
        Date parse = null;
        try {
            //将String类型的时间类型转换成Date类型
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(timess);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(parse);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static void test6() {
        Calendar c1 = Calendar.getInstance();   //当前日期
        Calendar c2 = Calendar.getInstance();   //昨天日期
        c2.setTime(getWorkingDayTimeByD(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));   //设置为另一个时间
        int year = c1.get(Calendar.HOUR_OF_DAY);
        int oldYear = c2.get(Calendar.HOUR_OF_DAY);
        System.out.println(year + 24 - oldYear);
    }

    public static Integer getHourCount() {
        Calendar c1 = Calendar.getInstance();   //当前日期
        Calendar c2 = Calendar.getInstance();   //昨天日期
        c2.setTime(new Date());
        c2.set(Calendar.HOUR_OF_DAY, 16);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.add(Calendar.DATE, -1);
        int nowHour = c1.get(Calendar.HOUR_OF_DAY);
        int oldHour = c2.get(Calendar.HOUR_OF_DAY);
        return nowHour + 24 - oldHour;
    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        Date date = test5();
//        System.out.println(date);
//        System.out.println(getWorkingDayTimeByD("2020-11-18"));
//          test6();
//        System.out.println( getHourCount());

        JSONObject json =  JSON.parseObject("{\"content\":{\"channel\":\"\",\"channelTransNo\":\"\",\"dealCode\":\"P2P2110\",\"dealMsg\":\"\",\"dealSts\":\"5\",\"settleType\":1,\"summary1\":\"\",\"summary2\":\"FJZF\",\"tradeType\":\"00\",\"transDate\":\"20201013\",\"transNo\":\"100120111902211605776643\",\"transTime\":\"182440\"},\"serviceCode\":\"RECHARGE_NOTIFY\",\"tradeType\":\"00\",\"transNo\":\"100120111902211605776643\"}");
        String content = json.getString("content");
        JSONObject json1 =  JSON.parseObject(content);
        System.out.println(json1.getString("settleType"));
    }
}
