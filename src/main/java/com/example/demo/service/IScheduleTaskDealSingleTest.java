package com.example.demo.service;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component("iScheduleTaskDealSingleTest")
public class IScheduleTaskDealSingleTest implements IScheduleTaskDealSingle {

     @Override
     public boolean execute(Object o, String s) throws Exception {
          return false;
     }

     @Override
     public List selectTasks(String s, String s1, int i, List list, int i1) throws Exception {
          return null;
     }

     @Override
     public Comparator getComparator() {
          return null;
     }
}