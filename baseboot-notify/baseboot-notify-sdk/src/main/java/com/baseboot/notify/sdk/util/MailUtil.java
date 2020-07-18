/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.util;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class MailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);


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


	public static Map<String, Object> convertJsonToMap(String jsonStr)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
		});
	}


	public static Map<String, Object> convertObjToMap(Object data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jnode = mapper.valueToTree(data);
		return mapper.readValue(jnode.textValue(), new TypeReference<Map<String, Object>>() {
		});
	}

}