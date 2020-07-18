/**
 * Copyright 2019. 
 */
package com.util;


import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class HttpRestUtil.
 *
 * @author mary.jane
 * @since Jan 16, 2019
 */
public class HttpRestUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRestUtil.class);


	/**
	 * Instantiates a new http rest util.
	 */
	private HttpRestUtil() {
		throw new IllegalStateException(HttpRestUtil.class.getName());
	}


	/**
	 * Gets the service URI.
	 *
	 * @param url the url
	 * @param obj the obj
	 * @return the service URI
	 */
	public static String getServiceURI(String url, Map<String, Object> obj) {
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		sb.append(url);

		if (!BaseUtil.isObjNull(obj)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			JsonNode jnode = mapper.valueToTree(obj);

			try {
				Map<String, Object> maps = mapper.readValue(jnode.toString(),
						new TypeReference<Map<String, Object>>() {
						});
				for (Map.Entry<String, Object> entry : maps.entrySet()) {
					String mKey = entry.getKey();
					Object mValue = entry.getValue();
					if (!BaseUtil.isObjNull(mValue) && !BaseUtil.isEquals(mKey, "serialVersionUID")) {
						if (isFirst) {
							sb.append("?");
							isFirst = false;
						}
						if (mValue instanceof String) {
							mValue = UriUtil.getVariableValueAsString(mValue);
						}
						sb.append(mKey + "=" + mValue + "&");
					}
				}
			} catch (JsonParseException e) {
				LOGGER.error("JsonParseException: {}", e.getMessage());
			} catch (JsonMappingException e) {
				LOGGER.error("JsonMappingException: {}", e.getMessage());
			} catch (IOException e) {
				LOGGER.error("IOException: {}", e.getMessage());
			}
		}
		return !isFirst ? (sb.toString()).substring(0, sb.length() - 1) : sb.toString();
	}

}
