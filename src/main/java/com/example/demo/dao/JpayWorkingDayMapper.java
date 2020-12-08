package com.example.demo.dao;

import com.example.demo.bean.JpayWorkingDayResInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JpayWorkingDayMapper {

   List<JpayWorkingDayResInfo> selectWorkDay();

}
