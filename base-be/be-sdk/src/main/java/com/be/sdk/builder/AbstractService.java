/**
 * Copyright 2019
 */
package com.be.sdk.builder;


import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.be.sdk.client.BeRestTemplate;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.BaseUtil;
import com.util.UriUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public abstract class AbstractService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);


	public abstract BeRestTemplate restTemplate();


	public abstract String url();


	protected String getServiceURI(String serviceName) {
		String uri = url() + serviceName;
		LOGGER.debug("Service Rest URL: {}", uri);
		return uri;
	}


	protected String getServiceURI(String serviceName, Map<String, Object> obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		JsonNode jnode = mapper.valueToTree(obj);
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		sb.append(getServiceURI(serviceName));

		if (!BaseUtil.isObjNull(obj)) {
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
