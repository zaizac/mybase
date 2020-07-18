/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.config.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class JsonRedisSerializer implements RedisSerializer<Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonRedisSerializer.class);

	private final ObjectMapper om;


	public JsonRedisSerializer() {
		this.om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}


	@Override
	public byte[] serialize(Object t) throws SerializationException {
		try {
			return om.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}


	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}

		try {
			return om.readValue(bytes, Object.class);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw new SerializationException(e.getMessage(), e);
		}
	}

}
