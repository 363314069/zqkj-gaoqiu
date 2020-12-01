package com.zqkj.utils;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class Gson2JsonRedisSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private Class<T> clazz;

	public Gson2JsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return GsonUtil.toJson(t).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);
		return (T) GsonUtil.fromJson(str, clazz);
	}
}