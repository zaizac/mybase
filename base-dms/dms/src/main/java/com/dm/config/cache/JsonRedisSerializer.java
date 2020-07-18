/**
 * Copyright 2019. 
 */
package com.dm.config.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;


/**
 * The Class JsonRedisSerializer.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class JsonRedisSerializer implements RedisSerializer<Object> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonRedisSerializer.class);

	/** The om. */
	private final ObjectMapper om;


	/**
	 * Instantiates a new json redis serializer.
	 */
	public JsonRedisSerializer() {
		om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}


	/* (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Object t) {
		try {
			return om.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
	 */
	@Override
	public Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		try {
			return om.readValue(bytes, Object.class);
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return null;
		}
	}

}
