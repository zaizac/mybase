/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.web.util;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * Utility class to handle Json object converts from Json to POJO or vice versa
 *
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper defaultObjectMapper = new ObjectMapper();

	private static volatile ObjectMapper objectMapper = null;


	// Ensures that there always is *a* object mapper
	private static ObjectMapper mapper() {
		if (objectMapper == null) {
			return defaultObjectMapper;
		} else {
			return objectMapper;
		}
	}


	/**
	 * Convert an object to JsonNode.
	 *
	 * @param data
	 *             Value to convert in Json.
	 */
	public static JsonNode toJsonNode(final Object data) {
		try {
			return mapper().valueToTree(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Convert a JsonNode to a Java value
	 *
	 * @param json
	 *             Json value to convert.
	 * @param clazz
	 *             Expected Java value type.
	 */
	public static <A> A fromJsonNode(JsonNode json, Class<A> clazz) {
		try {
			return mapper().treeToValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Transfer Json object to Java Object
	 *
	 * @param json
	 * @param clazz
	 *             - Entity or Class Type
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <A> A transferToObject(JsonNode json, Class<A> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper().readValue(json.toString(), clazz);
	}


	/**
	 * Transfer Json String to Java Object
	 *
	 * @param json
	 * @param clazz
	 *             - Entity or Class Type
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */

	public static <A> A transferToObject(String json, Class<A> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper().readValue(json, clazz);
	}


	/**
	 * Transfer Json object to Java List
	 *
	 * @param json
	 *             - JsonNode
	 * @param clazz
	 *             - Entity or Class Type
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<?> transferToList(JsonNode json, Class clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return (List<?>) mapper().readValue(json.toString(),
				TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
	}


	public static void setObjectMapper(ObjectMapper mapper) {
		objectMapper = mapper;
	}


	public static Map<String, Object> convertJsonToMap(String jsonStr)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper().readValue(jsonStr, new TypeReference<Map<String, Object>>() {
		});
	}


	public static String convertMapToJson(Map<String, Object> map)
			throws JsonGenerationException, JsonMappingException, IOException {
		return mapper().writeValueAsString(map);
	}


	public static String requestParamsToJSON(Map<String, String[]> params) throws JsonProcessingException {
		return mapper().writeValueAsString(params);
	}
}