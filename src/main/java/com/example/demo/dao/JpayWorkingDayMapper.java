package com.example.demo.dao;

import com.example.demo.bean.JpayWorkingDayResInfo;
import com.example.demo.util.AccountBillTradeBlotter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JpayWorkingDayMapper {

   List<JpayWorkingDayResInfo> selectWorkDay();
   void dealAmsCompsntasklockStatus();

   List<AccountBillTradeBlotter> selectRechargeByDate(@Param("busiCreateTimeBegin") String busiCreateTimeBegin, @Param("busiCreateTimeEnd") String busiCreateTimeEnd,
                                                      @Param("accUpdateTime") String accUpdateTime, @Param("accUpdateTimeEnd") String accUpdateTimeEnd);



}
