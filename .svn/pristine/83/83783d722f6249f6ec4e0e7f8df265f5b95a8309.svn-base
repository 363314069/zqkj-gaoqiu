package com.zqkj.utils;

import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gson类库的封装工具类，专门负责解析json数据</br> 内部实现了Gson对象的单例
 * 
 * @version 1.0
 * @since 2012-9-18
 */
public class GsonUtil {

	private static Gson gson = null;

	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private GsonUtil() {

	}

	public static Gson getGson() {
		return gson;
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	public static <T> T fromJson(Reader json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(Reader json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	public static <T> T fromJson(JsonReader reader, Type typeOfT) {
		return gson.fromJson(reader, typeOfT);
	}

	public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(JsonElement json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	public static JsonElement toJsonTree(Object src) {
		return gson.toJsonTree(src);
	}

	public static JsonElement toJsonTree(Object src, Type typeOfSrc) {
		return gson.toJsonTree(src, typeOfSrc);
	}

	public static String toJson(Object src) {
		return gson.toJson(src);
	}

	public static String toJson(Object src, Type typeOfSrc) {
		return gson.toJson(src, typeOfSrc);
	}

	public static void toJson(Object src, Appendable writer) {
		gson.toJson(src, writer);
	}

	public static void toJson(Object src, Type typeOfSrc, Appendable writer) {
		gson.toJson(src, typeOfSrc, writer);
	}

	public static void toJson(Object src, Type typeOfSrc, JsonWriter writer) {
		gson.toJson(src, typeOfSrc, writer);
	}

	public static String toJson(JsonElement jsonElement) {
		return gson.toJson(jsonElement);
	}

	public static void toJson(JsonElement jsonElement, Appendable writer) {
		gson.toJson(jsonElement, writer);
	}

	public static void toJson(JsonElement jsonElement, JsonWriter writer) {
		gson.toJson(jsonElement, writer);
	}
}
