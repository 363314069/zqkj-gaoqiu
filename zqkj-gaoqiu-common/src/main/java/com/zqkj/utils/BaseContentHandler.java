package com.zqkj.utils;

import java.util.HashMap;
import java.util.Map;

import com.zqkj.utils.jwt.JWTInfo;

/**
 * Created by ace on 2017/9/8.
 */
public class BaseContentHandler {
	public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();
	/** 当前用户ID KEY **/
	public static final String KEY_USER_ID = "id";
	/** 当前用户GUID KEY **/
	public static final String KEY_USER_GUID = "guid";
	/** 当前用户姓名 KEY **/
	public static final String KEY_USER_NAME = "name";
	/** 当前用户帐号 KEY **/
	public static final String KEY_USER_LOGINNAME = "loginName";
	/** 当前用户Token KEY **/
	public static final String KEY_USER_TOKEN = "token";
	/** 当前用户组织ID KEY **/
	public static final String KEY_USER_ORGANIZATIONID = "organizationId";
	/** JWTInfo KEY **/
	public static final String KEY_USER_JWTINFO = "jwtInfo";
	/** 当前用户组织GUID KEY **/
	public static final String KEY_USER_ORGANIZATIONGUID = "organizationGuid";
	/** 当前用户登录ID **/
	public static final String KEY_USER_IP = "userip";
	/** 当前用户登录OPENID **/
	public static final String KEY_USER_OPENID = "openid";
	
	public static void set(String key, Object value) {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		map.put(key, value);
	}

	public static Object get(String key) {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		return map.get(key);
	}

	public static void setUserId(Integer userId) {
		set(KEY_USER_ID, userId);
	}

	public static Integer getUserId() {
		Object value = get(KEY_USER_ID);
		return (Integer)value;
	}
	
	public static void setUserGuid(String userGuid) {
		set(KEY_USER_GUID, userGuid);
	}

	public static String getUserGuid() {
		Object value = get(KEY_USER_GUID);
		return StringUtil.getStringValue(value);
	}
	public static void setUserName(String userName) {
		set(KEY_USER_NAME, userName);
	}

	public static String getUserName() {
		Object value = get(KEY_USER_NAME);
		return StringUtil.getStringValue(value);
	}

	public static void setLoginName(String loginName) {
		set(KEY_USER_LOGINNAME, loginName);
	}
	
	public static String getLoginName() {
		Object value = get(KEY_USER_LOGINNAME);
		return StringUtil.getStringValue(value);
	}

	public static void setToken(String token) {
		set(KEY_USER_TOKEN, token);
	}

	public static String getToken() {
		Object value = get(KEY_USER_TOKEN);
		return StringUtil.getStringValue(value);
	}
	public static void setIp(String ip) {
		set(KEY_USER_IP, ip);
	}

	public static String getIp() {
		Object value = get(KEY_USER_IP);
		return StringUtil.getStringValue(value);
	}
	
	public static void setOrganizationGuid(String OrganizationGuid) {
		set(KEY_USER_ORGANIZATIONGUID, OrganizationGuid);
	}

	public static String getOrganizationGuid() {
		Object value = get(KEY_USER_ORGANIZATIONGUID);
		return StringUtil.getStringValue(value);
	}
	
	public static void setOrganizationId(Integer organizationId) {
		set(KEY_USER_ORGANIZATIONID, organizationId);
	}

	public static Integer getOrganizationId() {
		Object value = get(KEY_USER_ORGANIZATIONID);
		return StringUtil.getIntegerValue(value);
	}
	
	public static void setJWTInfo(JWTInfo jwtInfo) {
		set(KEY_USER_JWTINFO, jwtInfo);
	}
	
	public static JWTInfo getJWTInfo() {
		JWTInfo jwtInfo = (JWTInfo) get(KEY_USER_JWTINFO);
		return jwtInfo;
	}
	public static void remove() {
		threadLocal.remove();
	}

	public static void setOpenid(String openid) {
		set(KEY_USER_OPENID, openid);
	}

	public static String getOpenid() {
		Object value = get(KEY_USER_OPENID);
		return StringUtil.getStringValue(value);
	}
}
