package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProFileValue {
    @Value("${demo.age}")
    private Integer age;
    @Value("${demo.name}")
    private String name;

    @Override
    public String toString() {
        return "ProFileValue{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
