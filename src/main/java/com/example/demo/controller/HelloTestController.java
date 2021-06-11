package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


public class HelloTestController {

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String test() {
        return "{'sts': '000000'}";
    }
}
