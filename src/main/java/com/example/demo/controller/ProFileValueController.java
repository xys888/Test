package com.example.demo.controller;

import com.example.demo.util.ProFileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置文件中的值v
 * @Autor xue
 */

@RestController
@RequestMapping("/value")
public class ProFileValueController {

    @Value("${frees}")
    private String ss;

    @Autowired
    private ProFileValue proFileValue;

    @RequestMapping(value = "/test1")
    public String get1() {
        return ss;
    }

    @RequestMapping("/test2")
    public String get2() {
        return proFileValue.toString();
    }
}
