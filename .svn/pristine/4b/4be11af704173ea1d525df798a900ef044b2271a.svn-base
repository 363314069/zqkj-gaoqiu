package com.zqkj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.exception.BaseException;

public class MD5 {
	/**
     * 生成含有随机盐的密码
     */
	public static String md5Salt(String password) {
		if(StringUtils.isBlank(password)){
			return null;
		}
		Random r = new Random();
		int salt = r.nextInt(255) + 1;
		byte[] b = md5Binary(password + salt);
		int n = b[0] % 15;
		n = n < 0 ? n * -1 + 1 : n + 1;
		b[n] = (byte) (b[0] ^ salt);
		return HexUtil.toHex(b);
	}
	 /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
    	byte[] b = HexUtil.toBinary(md5);
    	int n = b[0] % 15;
		n = n < 0 ? n * -1 + 1 : n + 1;
		int salt = (b[0] ^ b[n]) & 0xff;
		b = md5Binary(password + salt);
		b[n] = (byte) (b[0] ^ salt);
        return HexUtil.toHex(b).equals(md5);
    }
    /**
     * 获取十六进制字符串形式的MD5摘要
     */
	public static String md5Hex(String str) {
		return HexUtil.toHex(md5Binary(str));
	}
    /**
     * 获取byte数组形式的MD5摘要
     */
	public static byte[] md5Binary(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte b[] = md.digest(str.getBytes());
			return b;
		} catch (NoSuchAlgorithmException e) {
			throw new BaseException("MD5加密异常!");
		}
	}
	public static void main(String[] arg) {
		/*
		String md5 = MD5.md5Salt("admin");
		System.out.println(md5);
		System.out.println(StringUtil.generateGUID());
		System.out.println(MD5.verify("admin", md5));
		//1483BJ981645Y!U#N@I$Y2018092915
		System.out.println(MD5.md5Hex("yycj080421cj"));
		*/
		
		System.out.println(MD5.verify("123456", "129164379a8baa6b875bddc5653ec3a2"));
		System.out.println(MD5.md5Hex("005455").toUpperCase());
	}
}
