/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.config.cache;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class JsonRedisSerializer implements RedisSerializer<Object> {

	private final ObjectMapper om;


	public JsonRedisSerializer() {
		om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}


	@Override
	public byte[] serialize(Object t) {
		try {
			return om.writeValueAsBytes(t);
		} catch (JsonProcessingException e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}


	@Override
	public Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		try {
			return om.readValue(bytes, Object.class);
		} catch (Exception e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}

}
