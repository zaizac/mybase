/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.util;


import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UriUtil {

	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");


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


	private static String getVariableValueAsString(Object variableValue) {
		try {
			return (variableValue != null ? URLEncoder.encode(variableValue.toString(), "UTF-8") : "");
		} catch (Exception e) {
			return (variableValue != null ? variableValue.toString() : "");
		}
	}


	private static String getVariableName(String match) {
		int colonIdx = match.indexOf(':');
		return (colonIdx != -1 ? match.substring(0, colonIdx) : match);
	}

}