/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.bff.config.cache.CustomRedisCacheManager;
import com.bff.config.cache.JsonRedisSerializer;
import com.bff.util.constants.CacheConstants;
import com.util.BaseUtil;
import com.util.CryptoUtil;
import com.util.constants.BaseConfigConstants;
import com.util.constants.BaseConstants;

import redis.clients.jedis.JedisPoolConfig;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
@Configuration
@EnableCaching
@EnableAspectJAutoProxy
public class RedisCacheConfig extends CachingConfigurerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);

	@Value("${" + BaseConfigConstants.REDIS_CONF_HOST + "}")
	private String redisHost;

	@Value("${" + BaseConfigConstants.REDIS_CONF_PORT + "}")
	private String redisPort;

	@Value("${" + BaseConfigConstants.REDIS_CONF_UNAME + "}")
	private String redisUname;

	@Value("${" + BaseConfigConstants.REDIS_CONF_PWORD + "}")
	private String redisPword;

	@Value("${" + BaseConfigConstants.SVC_IDM_SKEY + "}")
	private String skey;

	RedisCacheManager cacheManager;

	JedisConnectionFactory redisCf;


	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);

		redisCf = new JedisConnectionFactory(poolConfig);

		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("REDIS Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("Host : " + redisHost + BaseConstants.NEW_LINE);
		sb.append("Port : " + redisPort + BaseConstants.NEW_LINE);
		sb.append("Username : " + redisUname + BaseConstants.NEW_LINE);
		sb.append("Password : " + redisPword);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.debug("{}", sb);

		redisCf.setHostName(redisHost);
		redisCf.setPort(BaseUtil.getInt(redisPort));
		if (!BaseUtil.isObjNull(redisPword)) {
			redisCf.setPassword(CryptoUtil.decrypt(redisPword, skey));
		}

		redisCf.setUsePool(true);
		return redisCf;
	}


	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(cf);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JsonRedisSerializer());
		return redisTemplate;
	}


	@Bean
	public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
		cacheManager = new CustomRedisCacheManager(redisTemplate);

		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(CacheConstants.CACHE_DURATION_DAILY);
		clearCacheBucket(cacheManager);
		return cacheManager;
	}


	private void clearCacheBucket(CacheManager cacheManager) {
		Cache cache = cacheManager.getCache(CacheConstants.CACHE_BUCKET);
		if (!BaseUtil.isObjNull(cache)) {
			LOGGER.info("CLEARING CACHE BUCKET ~ {}", CacheConstants.CACHE_BUCKET);
			cache.clear();
		}
	}


	@PreDestroy
	public void destroy() {
		LOGGER.info("Destroying Spring Cache Redis");
		clearCacheBucket(cacheManager);
		redisCf.destroy();
	}

}