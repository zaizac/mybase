/**
 * Copyright 2019. 
 */
package com.dm.config.cache;


import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * The Class CustomRedisCacheManager.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class CustomRedisCacheManager extends RedisCacheManager {

	/** The template. */
	private final RedisTemplate<String, String> template;


	/**
	 * Instantiates a new custom redis cache manager.
	 *
	 * @param template
	 *             the template
	 */
	public CustomRedisCacheManager(RedisTemplate<String, String> template) {
		super(template);
		this.template = template;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.cache.support.AbstractCacheManager#getCache(java.
	 * lang.String)
	 */
	@Override
	public Cache getCache(String name) {
		return new RedisCacheWrapper(super.getCache(name), template);
	}

}