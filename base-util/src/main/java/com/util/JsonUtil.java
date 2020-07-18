/**
 * Copyright 2019. 
 */
package com.util;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.google.gson.JsonObject;


/**
 * Utility class to handle Json object converts from Json to POJO or vice versa.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class JsonUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	/** The Constant defaultObjectMapper. */
	private static final ObjectMapper defaultObjectMapper = objectMapper();

	/** The object mapper. */
	private static ObjectMapper objectMapper = null;


	/**
	 * Instantiates a new json util.
	 */
	private JsonUtil() {
		// DO NOTHING
	}


	/**
	 * Mapper.
	 *
	 * @return the object mapper
	 */
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
	 * @return the json node
	 */
	public static JsonNode toJsonNode(final Object data) {
		try {
			return mapper().valueToTree(data);
		} catch (Exception e) {
			LOGGER.error("{}", e.getMessage());
			return null;
		}
	}


	/**
	 * Convert a JsonNode to a Java value.
	 *
	 * @param <A>
	 *             the generic type
	 * @param json
	 *             Json value to convert.
	 * @param clazz
	 *             Expected Java value type.
	 * @return the a
	 */
	public static <A> A fromJsonNode(JsonNode json, Class<A> clazz) {
		try {
			return mapper().treeToValue(json, clazz);
		} catch (Exception e) {
			LOGGER.error("{}", e.getMessage());
			return null;
		}
	}


	/**
	 * Transfer Json object to Java Object.
	 *
	 * @param <A>
	 *             the generic type
	 * @param json
	 *             the json
	 * @param clazz
	 *             - Entity or Class Type
	 * @return the a
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */
	public static <A> A transferToObject(JsonNode json, Class<A> clazz) throws IOException {
		return mapper().readValue(json.toString(), clazz);
	}


	/**
	 * Transfer Json String to Java Object.
	 *
	 * @param <A>
	 *             the generic type
	 * @param json
	 *             the json
	 * @param clazz
	 *             - Entity or Class Type
	 * @return the a
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */

	public static <A> A transferToObject(String json, Class<A> clazz) throws IOException {
		return mapper().readValue(json, clazz);
	}


	/**
	 * Transfer Json object to Java List.
	 *
	 * @param json
	 *             - JsonNode
	 * @param clazz
	 *             - Entity or Class Type
	 * @return the list
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("rawtypes")
	public static List transferToList(JsonNode json, Class<?> clazz) throws IOException {
		return mapper().readValue(json.toString(),
				TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
	}


	/**
	 * Transfer Persistent List to Java List
	 * 
	 * @param source
	 *             - persistent object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List transferToList(List<?> source, Class<?> clazz) throws IOException {
		ObjectMapper objectMapper = objectMapper(true);
		String jsonStr = objectMapper.writeValueAsString(source);
		return objectMapper.readValue(jsonStr,
				TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
	}


	/**
	 * Transfer Persistent Object to Java Object
	 * 
	 * @param <A>
	 * @param source
	 *             - persistent object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <A> A transferToObject(Object source, Class<A> clazz) throws IOException {
		ObjectMapper objectMapper = objectMapper(true);
		String jsonStr = "";
		try {
			jsonStr = objectMapper.writeValueAsString(source);
		} catch (NoClassDefFoundError e) {
			objectMapper = objectMapper(false);
			jsonStr = objectMapper.writeValueAsString(source);
		}
		return objectMapper.readValue(jsonStr, clazz);
	}


	/**
	 * Sets the object mapper.
	 *
	 * @param mapper
	 *             the new object mapper
	 */
	public static void setObjectMapper(ObjectMapper mapper) {
		objectMapper = mapper;
	}


	/**
	 * Convert json to map.
	 *
	 * @param jsonStr
	 *             the json str
	 * @return the map
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */
	public static Map<String, Object> convertJsonToMap(String jsonStr) throws IOException {
		return mapper().readValue(jsonStr, new TypeReference<Map<String, Object>>() {
		});
	}


	/**
	 * Convert map to json.
	 *
	 * @param map
	 *             the map
	 * @return the string
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */
	public static String convertMapToJson(Map<String, Object> map) throws IOException {
		return mapper().writeValueAsString(map);
	}


	/**
	 * Request params to JSON.
	 *
	 * @param params
	 *             the params
	 * @return the string
	 */
	public static String requestParamsToJSON(Map<String, String[]> params) {
		JsonObject jsonObj = new JsonObject();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			String[] v = entry.getValue();
			Object o = (v.length == 1) ? v[0] : v;
			jsonObj.addProperty(entry.getKey(), String.valueOf(o));
		}
		return jsonObj.toString();
	}


	/**
	 * Object mapper.
	 *
	 * @return the object mapper
	 */
	public static ObjectMapper objectMapper() {
		return objectMapper(false);
	}


	public static ObjectMapper objectMapper(boolean hasModule) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_DEFAULT);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
		mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		if (hasModule) {
			mapper.registerModule(new Hibernate5Module());
		}
		return mapper;
	}

}