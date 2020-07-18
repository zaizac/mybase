/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.util;


import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baseboot.idm.sdk.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since 29/10/2016
 */
public class UriUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(UriUtil.class);

	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");


	private UriUtil() {
	}


	public static String expandUriComponent(String source, Map<String, Object> uriVariables) {
		if (source == null) {
			return null;
		}
		if (source.indexOf('{') == -1) {
			return source;
		}
		Matcher matcher = NAMES_PATTERN.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String match = matcher.group(1);
			String variableName = getVariableName(match);
			Object variableValue = uriVariables.get(variableName);
			String variableValueString = getVariableValueAsString(variableValue);
			String replacement = Matcher.quoteReplacement(variableValueString);
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}


	public static String getVariableValueAsString(Object variableValue) {
		String variable = BaseConstants.EMPTY_STRING;
		try {
			if (variableValue != null) {
				variable = URLEncoder.encode(variableValue.toString(), "UTF-8");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return variable;
	}


	private static String getVariableName(String match) {
		int colonIdx = match.indexOf(':');
		return (colonIdx != -1 ? match.substring(0, colonIdx) : match);
	}

}