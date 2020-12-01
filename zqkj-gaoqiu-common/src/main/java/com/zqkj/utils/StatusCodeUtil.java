package com.zqkj.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;

public class StatusCodeUtil {
	private static final String STATUS_CODE_PATH = "config/statuscode.properties";
	private static Props props = null;
	static {
		try {
			props = new Props(STATUS_CODE_PATH, CharsetUtil.CHARSET_UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getMsg(Integer code){
		return getMsg(code.toString());
	}
	public static String getMsg(String code){
		String msg = props.getProperty(code);
		if(msg == null)
			msg = getMsg(9001);
		return props.getProperty(code);
	}
	
	public static void main(String[] args) {
		System.err.println(StatusCodeUtil.getMsg(53027));
	}
}
