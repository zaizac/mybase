/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.config.cache;


import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.constants.IdmCacheConstants;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class RedisCacheWrapper implements Cache {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheWrapper.class);

	private final Cache cache;

	private final RedisTemplate<String, String> template;


	public RedisCacheWrapper(Cache cache, RedisTemplate<String, String> template) {
		this.cache = cache;
		this.template = template;
	}


	@Override
	public String getName() {
		return cache.getName();
	}


	@Override
	public Object getNativeCache() {
		return cache.getNativeCache();
	}


	@Override
	public ValueWrapper get(Object key) {
		try {
			return cache.get(key);
		} catch (Exception e) {
			try {
				return handleErrors(e);
			} catch (Exception e1) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e1.getMessage());
			}
			return null;
		}
	}


	@Override
	public <T> T get(Object key, Class<T> type) {
		return cache.get(key, type);
	}


	@Override
	public void put(Object key, Object value) {
		try {
			cache.put(key, value);
		} catch (Exception e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			try {
				handleErrors(e);
			} catch (Exception e1) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e1.getMessage());
			}
		}
	}


	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return cache.putIfAbsent(key, value);
	}


	@Override
	public void evict(Object key) {
		cache.evict(key);
	}


	public void evictByPrefix(String prefix) {
		Set<String> redisKey = template.keys(IdmCacheConstants.CACHE_PREFIX + "_" + prefix + "*");
		LOGGER.debug("redisKey: {} - {}", cache.getName(), new ObjectMapper().valueToTree(redisKey));
		if (!BaseUtil.isObjNull(redisKey)) {
			for (String key : redisKey) {
				if (!key.contains("~keys")) {
					template.delete(key);
					cache.evict(key);
				}
			}
		}
	}


	@Override
	public void clear() {
		cache.clear();
	}


	protected <T> T handleErrors(Exception e) {
		LOGGER.debug("Exception Thrown: {}", e.getMessage());
		return null;
	}


	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		return null;
	}

}
