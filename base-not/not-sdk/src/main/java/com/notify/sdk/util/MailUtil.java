/**
 * Copyright 2019. 
 */
package com.notify.sdk.util;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class MailUtil.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class MailUtil {

	/**
	 * Instantiates a new mail util.
	 */
	private MailUtil() {
		throw new IllegalStateException("MailUtil Utility class");
	}


	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);


	/**
	 * Convert map to json.
	 *
	 * @param map
	 *             the map
	 * @return the string
	 */
	public static String convertMapToJson(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter wr = new StringWriter();
		try {
			mapper.writeValue(wr, map);
		} catch (IOException e) {
			LOGGER.error("IOException: {}", e.getMessage());
		}
		return wr.toString();
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
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
		});
	}


	/**
	 * Convert obj to map.
	 *
	 * @param data
	 *             the data
	 * @return the map
	 * @throws IOException
	 *              Signals that an I/O exception has occurred.
	 */
	public static Map<String, Object> convertObjToMap(Object data) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jnode = mapper.valueToTree(data);
		return mapper.readValue(jnode.textValue(), new TypeReference<Map<String, Object>>() {
		});
	}

}