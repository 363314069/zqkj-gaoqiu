package com.zqkj.configuration;

import com.zqkj.utils.Gson2JsonRedisSerializer;
import com.zqkj.utils.GsonUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

//@Configuration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	@Resource
	private LettuceConnectionFactory lettuceConnectionFactory;
	private Duration timeToLive = Duration.ofSeconds(360);

	/**
	 * redis key生成策略
	 * target: 类
	 * method: 方法
	 * params: 参数
	 * @return KeyGenerator
	 * 
	 * 注意: 该方法只是声明了key的生成策略,还未被使用,需在@Cacheable注解中指定keyGenerator
	 *      如: @Cacheable(value = "key", keyGenerator = "cacheKeyGenerator")
	 */
    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            //sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
            	//由于参数可能不同, hashCode肯定不一样, 缓存的key也需要不一样
                sb.append(GsonUtil.toJson(obj).replaceAll("[\":\\{\\}]+", "="));
            }
            return sb.toString();
        };
    }

	/**
	 * 缓存配置管理器
	 */
    @Bean
	public CacheManager cacheManager() {
		// 关键点，spring
		// cache的注解使用的序列化都从这来，没有这个配置的话使用的jdk自己的序列化，实际上不影响使用，只是打印出来不适合人眼识别
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))// key序列化方式
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))// value序列化方式
				.disableCachingNullValues().entryTtl(timeToLive);// 缓存过期时间

		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(lettuceConnectionFactory).cacheDefaults(config).transactionAware();

		return builder.build();
	}
    /**
     * RedisTemplate配置 在单独使用redisTemplate的时候 重新定义序列化方式
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
    	RedisSerializer<Object> serializer = valueSerializer();
    	// 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(serializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(serializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	private RedisSerializer<Object> valueSerializer() {
		Gson2JsonRedisSerializer<Object> gson2JsonRedisSerializer = new Gson2JsonRedisSerializer<Object>(Object.class);
		return gson2JsonRedisSerializer;
	}
}