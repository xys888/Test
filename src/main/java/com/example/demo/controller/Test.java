package com.example.demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    public static void getExecutors() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "is running...");
                    System.out.println(Thread.currentThread().getName() + "is running...");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {

        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(timeStr);
        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(parse);
        getExecutors();
    }

}
