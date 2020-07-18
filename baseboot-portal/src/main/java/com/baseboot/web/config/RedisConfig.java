/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.config;


import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.util.BaseUtil;
import com.util.constants.BaseConstants;


/**
 * @author mary.jane
 * @since Oct 16, 2018
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

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

	JedisConnectionFactory redisCf;


	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		StringBuilder sb = new StringBuilder();
		sb.append(BaseConstants.NEW_LINE + BaseConstants.NEW_LINE);
		sb.append(BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		sb.append("REDIS Credentials");
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		RedisStandaloneConfiguration redisSC = new RedisStandaloneConfiguration(redisHost,
				BaseUtil.getInt(redisPort));
		sb.append("Host : " + redisHost + BaseConstants.NEW_LINE);
		sb.append("Port : " + redisPort + BaseConstants.NEW_LINE);
		sb.append("Username : " + redisUname + BaseConstants.NEW_LINE);
		sb.append("Password : " + redisPword);
		sb.append(BaseConstants.NEW_LINE + BaseConstants.LOG_SEPARATOR + BaseConstants.NEW_LINE);
		LOGGER.info(sb.toString());

		JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
		jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));

		return new JedisConnectionFactory(redisSC, jedisClientConfiguration.usePooling().build());
	}


	@Bean(name = "redisTemplate")
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(cf);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}


	@Bean(name = "cacheManager")
	public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(60)).disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		return RedisCacheManager.builder(jedisConnectionFactory).cacheDefaults(cacheConfiguration).build();
	}

}
