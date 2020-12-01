package com.zqkj.utils;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.zqkj.utils.captcha.LineCaptcha;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

public class CodeUtil {
	/**
	 * 不包含0,O,I,1,4共31字符
	 */
	private static final String CODE_STR = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	private static final char[] CODE_VAL = CODE_STR.toCharArray();
	//private static Captcha captcha = new Captcha(170, 100);
	//private static ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 5, 4);
	private static LineCaptcha captcha = new LineCaptcha(200, 100);
	
	//创建缓存，默认30毫秒过期
	//private static CodeCacheUtil timedCache = CodeCacheUtil.getInstance();
	private static Cache<String,String> fifoCache = CacheUtil.newFIFOCache(1000);

	/**
	 * 
	 * @param t		获取验证码时的时间参数
	 * @return
	 */
	public static BufferedImage getCodeImg() {
		return getCodeImg(getCodeText());
	}
	/**
	 * 
	 * @param code	获取验证码时的时间参数
	 * @return
	 */
	public static BufferedImage getCodeImg(String code) {
		BufferedImage image = (BufferedImage) captcha.createImage(code);
		return image;
	}
	/**
	 * 
	 * @param t		获取验证码时的时间参数
	 * @param code	验证码
	 * @return
	 */
	public static boolean verify(String code){
		if(code == null || code.length() < 3)
			return false;
		code = code.toUpperCase();
		code = fifoCache.get(code);
		if(code == null){
			return false;
		} else {
			fifoCache.remove(code);
		}
		int t = (int) (System.currentTimeMillis() / 60000 % 32);
		char[] c = code.toCharArray();
		int n[] = new int[c.length];
		int v = 0;
		for(int i = 0; i < c.length; i++){
			n[i] = CODE_STR.indexOf(c[i]);
			v = v ^ n[i];
		}
		return v == t || v == t - 1;
	}
	public static String getCodeText(){
		return getCodeText(5);
	}
	public static String getCodeText(int length) {
		if(length < 3){
			System.err.println("验证码最少3位数");
			return null;
		}
		Random rand = new Random();
		StringBuilder str = new StringBuilder();
		int t = (int) (System.currentTimeMillis() / 60000 % 32);
		int v = 0;
		for(int i = 0; i < length - 1; i++){
			v = rand.nextInt(32);
			t = t ^ v;
			str.append(CODE_VAL[v]);
		}
		str.append(CODE_VAL[t]);
		String code = str.toString().toUpperCase();
		fifoCache.put(code, code, DateUnit.SECOND.getMillis() * 30);
		return str.toString();
	}
	
	public static void main(String[] args) {
		TimedCache<String, String> timedCache = CacheUtil.newTimedCache(30);
		timedCache.put("aa", "aa1");
		System.out.println(timedCache.get("aa"));
		//CodeUtil.verify("544PWC");
	}
}
