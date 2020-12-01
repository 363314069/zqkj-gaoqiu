package com.zqkj.utils;


import java.util.Random;

/**
 * 随机数工具类
 * 
 */
public class RandomUtils {
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String NUMBER_CHAR = "0123456789";
    
    /**
     * 获取定长的随机数，包含大小写、数字
     * @param length
     * 				随机数长度
     * @return
     */
    public static String objString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，包含大小写字母
     * @param length
     * 				随机数长度
     * @return
     */
    public static String mixString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，只包含小写字母
     * @param length	
     * 				随机数长度
     * @return
     */
    public static String lowerString(int length) { 
        return mixString(length).toLowerCase(); 
    } 
    
    /**
     * 获取定长的随机数，只包含大写字母
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String upperString(int length) { 
        return mixString(length).toUpperCase(); 
    } 
    
    /**
     * 获取定长的随机数，只包含数字
     *
     * @param length
     * 				随机数长度
     * @return
     */
    public static String numberString(int length){
    	StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length()))); 
        } 
        return sb.toString(); 
    }
    
    /**
     * 
     * @param min //定义随机数的最小值
     * @param max //定义随机数的最大值
     * @return 随机数
     */
    public static int numberInt(int min,int max){
        //产生一个min~max的数
        int randomNum=(int)min+(int)(Math.random()*(max-min));
        if(randomNum%2==0)    //如果是偶数就输出
            return randomNum;
        else    //如果是奇数就加1后输出
        	return (randomNum+1);
    }

  
}
