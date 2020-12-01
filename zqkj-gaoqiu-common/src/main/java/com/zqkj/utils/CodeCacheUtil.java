package com.zqkj.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

public class CodeCacheUtil {
	private TimedCache<String, String> timedCache;
	private static CodeCacheUtil codeCache = new CodeCacheUtil();
	
	private CodeCacheUtil(){
		timedCache = CacheUtil.newTimedCache(30);
		timedCache.schedulePrune(30);
	}
	
	public static CodeCacheUtil getInstance(){
		return codeCache;
	}
	
	public void put(String key, String object){
		timedCache.put(key, object);
	}
	
	public String get(String key){
		String code = timedCache.get(key, false);
		timedCache.remove(key);
		return code;
	}
}
