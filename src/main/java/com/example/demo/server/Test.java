package com.example.demo.server;

import com.example.demo.util.DataDesenUtil;
import com.example.demo.util.DataUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2021年04月08日11:12
 */
public class Test {
    public static void main(String[] args) {
        String s = DataDesenUtil.desensBankCard("142727199709121109");
        String s1 = DataDesenUtil.encryptString("1427271997****1109",8,5);
        String s2 = DataUtil.encryptBankCard("142727199709121109");
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
    }

}
