package com.example.demo.service;

import com.example.demo.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestServiceImpl {

    @Autowired
    private TestDao testDao;

    public void updateInfo(Map<String, String> map) {
        String nameNew = map.get("name");
        testDao.updateInfo(nameNew);
    }
}
