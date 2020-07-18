/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.config.cache;


import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class CustomRedisCacheManager extends RedisCacheManager {

	private final RedisTemplate<String, String> template;


	public CustomRedisCacheManager(RedisTemplate<String, String> template) {
		super(template);
		this.template = template;
	}


	@Override
	public Cache getCache(String name) {
		return new RedisCacheWrapper(super.getCache(name), template);
	}

}