package com.example.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataUtil {
	/**
	 * 格式化类
	 */
	private static  DecimalFormat dfmt = new DecimalFormat();
	
	private static Logger log=LoggerFactory.getLogger(DataUtil.class);
	
	static DecimalFormat dfmtvar = new DecimalFormat();
	
	static{
		dfmt.applyPattern("########0.##");
	}
	
	/**
	 * double类型转换为保留两位小树的字符串
	 * @param arg  double数字
	 * @return  保留两位小数的字符串
	 */
	public static String formateToStr(double arg) {
		
		
		return dfmt.format(arg);
	}
	
	/**
	 * double类型转换为制定通配符类型的字符串
	 * @param arg  double数字
	 * @param strParttern  通配符
	 * @return  保留两位小数的字符串
	 */
	public static String formateToStr(double arg,String strParttern) {
		dfmtvar.applyPattern(strParttern);
		return dfmtvar.format(arg);
	}
	
	/**
	 * double类型转换指定小数点位数的的字符串
	 * @param arg  double数字
	 * @return  保留两位小数的字符串
	 */
	public static String formateToStr(double arg,int scale) {
		StringBuilder builderPartern = new StringBuilder("###,###,##0.");
		for (int i = 0; i < scale; i++) {
			builderPartern.append("0");
		}
		dfmtvar.applyPattern(builderPartern.toString());
		return dfmtvar.format(arg);
	}
	
	/**
	 * double类型数据四舍五入保留scale小数
	 *
	 * @param v1 数字
	 * @param scale 标度
	 * @return 保留scale位小数的double
	 */
	public static double formate(double v1,int scale) {
		
		BigDecimal b = BigDecimal.valueOf(v1);
		
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	/**
	 * BigDecimal类型数据四舍五入保留scale位小数
	 * @param v1 数字BigDecimal
	 * @param scale 标度
	 * @return 保留scale位小数的BigDecimal
	 */
	public static BigDecimal formate(BigDecimal v1,int scale) {
		return v1.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 数据比对 
	 * @param val1 比较数
	 * @param val2 被比较数
	 * @return 当 val1小于、等于、大于val2时，返回-1、0、1
	 */
	public static int toCompare(double val1,double val2) {
		BigDecimal dblVal1 = BigDecimal.valueOf(val1).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		BigDecimal dblVal2 = BigDecimal.valueOf(val2).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		int intRetrn = dblVal1.compareTo(dblVal2);
		return intRetrn;
	}
	
	/**
	 * 比较数值是否存在于两个数的范围内（闭区间）
	 * @param val 比较数
	 * @param min 最小值
	 * @param max 最大值
 	 * @param scale 标度
	 * @return true 在区间范围内，false表示不在区间范围内
	 */
	public static boolean toCompare(double val, double min, double max, int scale) {
		
		BigDecimal dblVal = BigDecimal.valueOf(val).setScale(scale, BigDecimal.ROUND_HALF_UP);
		BigDecimal minVal = BigDecimal.valueOf(min).setScale(scale,BigDecimal.ROUND_HALF_DOWN);
		BigDecimal maxVal = BigDecimal.valueOf(max).setScale(scale,BigDecimal.ROUND_HALF_DOWN);
		int com1 = dblVal.compareTo(minVal);
		int com2 = dblVal.compareTo(maxVal);
		if(com1 >= 0 && com2 <= 0){
			return true;
		}
		return false;
	}
	

	/**
	 * @Title: encryptString   
	 * @Description: 对字符串进行脱敏处理
	 * @param:  original 原字符串
	 * @param:  leftIndex 倒数位数--左侧
	 * @param:  rightIndex 倒数位数--右侧
	 * @return: String      
	 * @throws
	 */
    public static String encryptString(String original, int leftIndex, int rightIndex) {
        char[] cs = original.toCharArray();
        char[] encryptCs = new char[cs.length];
        int index = 0;
        for (int i=0; i<cs.length; i++) {
            index = cs.length - i;
            if (index>=leftIndex && index<=rightIndex) {
                encryptCs[i] = '*';
            } else {
                encryptCs[i] = cs[i];
            }
        }
        return new String(encryptCs);
    }
    /**
     * @Title: checkEncryptString   
     * @Description: 校验入参是否满足脱敏规则
     * @param:  original
     * @param:  leftIndex
     * @param:  rightIndex
     * @return: boolean      true校验通过，满足校验规则/false校验不通过
     * @throws
     */
    public static boolean checkEncryptString(String original, int leftIndex, int rightIndex) {
        char[] cs = original.toCharArray();
        int count = 0; // 匹配字符数
        int index = 0;
        for (int i=0; i<cs.length; i++) {
            index = cs.length - i;
            if ('*'== cs[i]) {
                if (index<leftIndex || index>rightIndex) { // 脱敏字符位置不正确
                    log.warn("脱敏字符位置不对:{}", original);
                    return false;
                }
                count ++;
            }
            if (count > (rightIndex - leftIndex + 1)) { // 脱敏字符个数不正确
                log.warn("脱敏字符个数不正确:{}", original);
                return false;
            }
        }
        if (count != (rightIndex - leftIndex + 1)) { // 脱敏字符个数不正确
            log.warn("脱敏字符个数不正确:{}", original);
            return false;
        }
        return true;
    }
    /**
     * @Title: encryptBankCard   
     * @Description: 对银行卡号进行脱敏处理，倒数5到8位脱敏为*号 
     * @param:  bankCard
     * @return: String      
     * @throws
     */
    public static String encryptBankCard(String bankCard) {
        return DataUtil.encryptString(bankCard, 5, 8);
    }
    /**
     * 
     * @Title: checkEncryptBankCard   
     * @Description: 校验银行卡号脱敏规则
     * @param: @param bankCard
     * @return: boolean      
     * @throws
     */
    public static boolean checkEncryptBankCard(String bankCard) {
        return DataUtil.checkEncryptString(bankCard, 5, 8);
    }
    
    /**
     * @Title: isEncryptString   
	 * @Description: 判断是否包含该字符窜
     * @param str
     * 			原字符串
     * @param lable
     * 			被包含字符串
     * @return
     */
    public static boolean isEncryptString(String str, String lable) {
    	return str.indexOf(lable) > -1;
    }
    
    /**
     * @Title: isEncryptBankCard   
	 * @Description: 判断是否脱敏卡号
     * @param bankCard
     * 			银行卡
     * @return
     */
    public static boolean isEncryptBankCard(String bankCard) {
//    	if(StringUtils.isNotBlank(bankCard)) {
//    		return isEncryptString(bankCard, "**");
//    	}
    	return false;
    }

}