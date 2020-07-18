/**
 * Copyright 2019. 
 */
package com.dm.config.cache;


import java.util.Set;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import com.dm.sdk.client.constants.DmErrorCodeEnum;
import com.dm.sdk.exception.DmException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.constants.IdmCacheConstants;
import com.util.BaseUtil;


/**
 * The Class RedisCacheWrapper.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class RedisCacheWrapper implements Cache {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheWrapper.class);

	/** The cache. */
	private final Cache cache;

	/** The template. */
	private final RedisTemplate<String, String> template;


	/**
	 * Instantiates a new redis cache wrapper.
	 *
	 * @param cache the cache
	 * @param template the template
	 */
	public RedisCacheWrapper(Cache cache, RedisTemplate<String, String> template) {
		this.cache = cache;
		this.template = template;
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#getName()
	 */
	@Override
	public String getName() {
		return cache.getName();
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#getNativeCache()
	 */
	@Override
	public Object getNativeCache() {
		return cache.getNativeCache();
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public ValueWrapper get(Object key) {
		try {
			return cache.get(key);
		} catch (Exception e) {
			try {
				return handleErrors(e);
			} catch (Exception e1) {
				LOGGER.error("Exception", e);
			}
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#get(java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T> T get(Object key, Class<T> type) {
		return cache.get(key, type);
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(Object key, Object value) {
		try {
			cache.put(key, value);
		} catch (Exception e) {
			try {
				handleErrors(e);
			} catch (Exception e1) {
				LOGGER.error(e.getMessage());
			}
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return cache.putIfAbsent(key, value);
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#evict(java.lang.Object)
	 */
	@Override
	public void evict(Object key) {
		cache.evict(key);
	}


	/**
	 * Evict by prefix.
	 *
	 * @param prefix the prefix
	 */
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


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#clear()
	 */
	@Override
	public void clear() {
		cache.clear();
	}


	/**
	 * Handle errors.
	 *
	 * @param <T> the generic type
	 * @param e the e
	 * @return the t
	 */
	protected <T> T handleErrors(Exception e) {
		try {
			LOGGER.debug("Exception Thrown: {}", e.getMessage());

			if (e instanceof RuntimeException) {
				return null;
			} else {
				throw e;
			}
		} catch (Exception ex) {
			throw new DmException(DmErrorCodeEnum.E503DOMGEN, new String[] { ex.getMessage() });
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.cache.Cache#get(java.lang.Object, java.util.concurrent.Callable)
	 */
	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		return null;
	}

}
