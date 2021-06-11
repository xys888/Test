package com.example.demo.service;

import com.example.demo.dao.DayBalancePushDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @date 2021年04月08日11:29
 */
@Component
public class DayBalancePushService {
   @Autowired
   private DayBalancePushDao dayBalancePushDao;

   public void update(){
       dayBalancePushDao.updateDayBalancePushDao1();
       dayBalancePushDao.updateDayBalancePushDao2();
       dayBalancePushDao.updateDayBalancePushDao3();
   }


}
