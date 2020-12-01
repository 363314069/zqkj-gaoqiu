package com.zqkj.utils;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapUtil extends cn.hutool.core.map.MapUtil{

	
	@SuppressWarnings("unchecked")
	public  static Map<String,Object> objToMap(Object fromValue){
		Map<String,Object> map = null;
		if(null == fromValue)return null;
		ObjectMapper objectMapper =  new ObjectMapper();
		//Object to Map<String, Object>
		try {
			map = objectMapper.convertValue(fromValue, Map.class);
		} catch (IllegalArgumentException e) {
			return map;
		}
		
		return map;
	}
	
}
