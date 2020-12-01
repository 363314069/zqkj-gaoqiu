package com.zqkj.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public static final String CODE_KEY = "code";
	public static final int CODE_OK_VALUE = 0;
	public static final String MSG_KEY = "msg";
	public static final String ERROR_KEY = "error";
	public static final String DATA_KEY = "data";
	public static final String UNKNOWN_ERROR = "未知错误，请联系管理员";
	
	public R() {
		put(CODE_KEY, CODE_OK_VALUE);
	}
	
	public static R error() {
		return error(Content.EX_OTHER_CODE, UNKNOWN_ERROR);
	}
	
	public static R error(int code) {
		return error(code, StatusCodeUtil.getMsg(code));
	}
	
	public static R error(String msg) {
		return error(Content.EX_OTHER_CODE, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public R putData(Object value) {
		super.put(DATA_KEY, value);
		return this;
	}
	
	public R putError(Object value) {
		super.put(ERROR_KEY, value);
		return this;
	}
}
