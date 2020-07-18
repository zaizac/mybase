package com.idm.sdk.builder;


import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.sdk.client.IdmRestTemplate;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.UriUtil;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 * @author muhammad.hafidz
 * @since 10 Apr 2019
 */
public abstract class AbstractBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBuilder.class);


	public abstract IdmRestTemplate restTemplate();


	public abstract String url();


	protected String getServiceURI(String serviceName) {
		String uri = url() + serviceName;
		LOGGER.info("Service Rest URL: {}", uri);
		return uri;
	}


	protected String getServiceURI(String serviceName, Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append(getServiceURI(serviceName));
		boolean isFirst = true;

		if (!BaseUtil.isObjNull(obj)) {
			isFirst = assignQueryParameter(JsonUtil.objectMapper(), obj, sb);
		}
		return !isFirst ? (sb.toString()).substring(0, sb.length() - 1) : sb.toString();
	}


	/**
	 * @param mapper
	 * @param obj
	 * @param sb
	 *             query parameter string
	 * @return true if empty query parameter
	 */
	private boolean assignQueryParameter(ObjectMapper mapper, Object obj, StringBuilder sb) {
		boolean isFirst = true;
		try {
			JsonNode jnode = mapper.valueToTree(obj);
			Map<String, Object> maps = mapper.readValue(jnode.toString(), new TypeReference<Map<String, Object>>() {
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
					sb.append(mKey).append("=").append(mValue).append("&");
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return isFirst;
	}

}
