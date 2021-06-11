package com.example.demo.util;


/**
 * 数据脱敏工具类
 */
public class DataDesenUtil {

    private static final int DEFAULT_SIZE = 6;
    private static final String DEFAULT_SYMBOL = "*";

    /**
     * @Title: encryptString
     * @Description: 对字符串进行脱敏处理
     * @param:  original 原字符串
     * @param:  leftIndex 左侧开始位置
     * @param:  rightIndex 右侧截止位置
     * @return: String
     * @throws
     */
    public static String encryptString(String original, int leftIndex, int rightIndex) {
        char[] cs = original.toCharArray();
        char[] encryptCs = new char[cs.length];
        for (int i=0; i < cs.length; i++) {
            if (i>=leftIndex && i<=rightIndex) {
                encryptCs[i] = '*';
            } else {
                encryptCs[i] = cs[i];
            }
        }
        return new String(encryptCs);
    }

    public static String desensBankCard(String value){
            if(value == null || "".equals(value)){
                return "";
            }
        return DataUtil.encryptBankCard(value);
    }

    public static String desensCertNo(String value){
        if(value == null || "".equals(value)){
            return "";
        }
        if(value.length() > 15){
            // return encryptString(value, 6, 13);
            return DataUtil.encryptBankCard(value);
        }else{
            //return  encryptString(value, 6, 11);
            return DataUtil.encryptBankCard(value);
        }
    }

    public static String desensPhoneNo(String value){
        if(value == null || "".equals(value)){
            return "";
        }
        return  encryptString(value, 3, 6);
    }
}
