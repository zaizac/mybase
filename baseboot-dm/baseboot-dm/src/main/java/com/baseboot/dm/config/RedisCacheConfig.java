/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.config;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.baseboot.dm.config.cache.CustomRedisCacheManager;
import com.baseboot.dm.config.cache.JsonRedisSerializer;
import com.baseboot.dm.sdk.client.constants.DmCacheConstants;
import com.baseboot.dm.sdk.util.BaseUtil;
import com.baseboot.idm.sdk.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Configuration
@EnableCaching
public class RedisCacheConfig implements DmCacheConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);

	@Value("${" + ConfigConstants.REDIS_CONF_DEFAULT + "}")
	private String redisDefault;

	@Value("${" + ConfigConstants.REDIS_CONF_HOST + "}")
	private String redisHost;

	@Value("${" + ConfigConstants.REDIS_CONF_PORT + "}")
	private String redisPort;

	@Value("${" + ConfigConstants.REDIS_CONF_SENTINEL_MASTER + "}")
	private String redisSentinelMaster;

	@Value("${" + ConfigConstants.REDIS_CONF_SENTINEL_HOST + "}")
	private String redisSentinelHost;

	@Value("${" + ConfigConstants.REDIS_CONF_SENTINEL_PORT + "}")
	private String redisSentinelPort;

	@Value("${" + ConfigConstants.REDIS_CONF_UNAME + "}")
	private String redisUname;

	@Value("${" + ConfigConstants.REDIS_CONF_PWORD + "}")
	private String redisPword;

	RedisCacheManager cacheManager;

	JedisConnectionFactory redisCf;


	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(100);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxIdle(5);

		redisCf = new JedisConnectionFactory(poolConfig);

		StringBuffer sb = new StringBuffer();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("REDIS Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		if (BaseUtil.isEqualsCaseIgnore("N", redisDefault)) {
			RedisSentinelConfiguration redisSC = new RedisSentinelConfiguration();
			String[] host = redisSentinelHost.split(",");
			String[] port = redisSentinelPort.split(",");
			for (int i = 0; i < host.length; i++) {
				sb.append("Host : " + host[i] + BaseConstants.NEW_LINE);
				sb.append("Port : " + port[i]);
				redisSC.addSentinel(new RedisNode(host[i], BaseUtil.getInt(port[i])));
			}
			redisCf = new JedisConnectionFactory(redisSC, poolConfig);
		} else {
			sb.append("Host : " + redisHost + BaseConstants.NEW_LINE);
			sb.append("Port : " + redisPort + BaseConstants.NEW_LINE);
			sb.append("Username : " + redisUname + BaseConstants.NEW_LINE);
			sb.append("Password : " + redisPword);
			redisCf.setHostName(redisHost);
			redisCf.setPort(BaseUtil.getInt(redisPort));
		}
		if (!BaseUtil.isObjNull(redisPword)) {
			redisCf.setPassword(redisPword);
		}
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());
		redisCf.setUsePool(true);
		return redisCf;
	}


	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(cf);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JsonRedisSerializer());
		return redisTemplate;
	}


	@Bean
	public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
		cacheManager = new CustomRedisCacheManager(redisTemplate);

		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(CACHE_DURATION_DAILY);
		clearCacheBucket(cacheManager);
		return cacheManager;
	}


	private void clearCacheBucket(CacheManager cacheManager) {
		Cache cache = cacheManager.getCache(CACHE_BUCKET);
		if (!BaseUtil.isObjNull(cache)) {
			LOGGER.info("CLEARING CACHE BUCKET ~ {}", CACHE_BUCKET);
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