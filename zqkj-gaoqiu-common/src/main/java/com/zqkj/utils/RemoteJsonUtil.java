package com.zqkj.utils;

import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class RemoteJsonUtil {

	private JSONObject jsonObj;
	
	private RemoteJsonUtil(String json){
		try {
			this.jsonObj = new JSONObject(json);
		} catch (Exception e) {
			this.jsonObj = new JSONObject();
			this.jsonObj.append(Content.KEY_CODE_NAME, Content.EX_OTHER_CODE);
			this.jsonObj.append(Content.KEY_MSG_NAME, json);
		}
	}
	
	public static  RemoteJsonUtil init(String json){
		return new RemoteJsonUtil(json);
	}
	
	public String getDate(){
		return jsonObj.getStr(Content.KEY_DATA_NAME);
	}

	public <T> T getDate(Class<T> type){
		return jsonObj.get(Content.KEY_DATA_NAME, type);
	}
	
	public Integer getCode(){
		return jsonObj.getInt(Content.KEY_CODE_NAME);
	}
	
	public String getMsg(){
		return jsonObj.getStr(Content.KEY_MSG_NAME);
	}
	
	public <T> T getMsg(Class<T> type){
		return jsonObj.get(Content.KEY_MSG_NAME, type);
	}
	
	public String getStr(String key){
		return jsonObj.getStr(key);
	}
	
	public <T> T get(String key, Class<T> type){
		return jsonObj.get(key, type);
	}
	

	public  Boolean isNull(){
		if(null == this.getCode() || this.getCode() != 0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	

	public  <T> List<T> strToList(String key,Class<T> clazz){
		JSONArray arr = jsonObj.getJSONArray(key);
		if(arr.size() <= 0){
			return null;
		}
		return  arr.toList(clazz);
	}
	
	
	public  JSONArray strToJSONArray(String key){
		return  jsonObj.getJSONArray(key);
	}
	
	
	
}
