package com.example.demo.controller;

import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.service.JpayWorkingDayService;
import com.example.demo.service.JpayWorkingDayService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JpayWorkingDayService jpayWorkingDayService;
    @Autowired
    private JpayWorkingDayService1 jpayWorkingDayService1;

    @ResponseBody
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public List<JpayWorkingDayResInfo> getJapyWorkingInfo(){
        List<JpayWorkingDayResInfo> jpayWorkingDayResInfos = jpayWorkingDayService.selectWorkDay();
        return jpayWorkingDayResInfos;
    }

    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public String set(){
        jpayWorkingDayService.set1();
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public Map<String, String> get(String timess,Integer args){
        Map<String, String> map = jpayWorkingDayService1.getWorkingDayTime(timess,args);
        return map;
    }

    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    public void getCss(){
        jpayWorkingDayService.getCss();
    }
}
