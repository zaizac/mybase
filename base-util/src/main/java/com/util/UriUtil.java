/**
 * Copyright 2019. 
 */
package com.util;


import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.constants.BaseConstants;


/**
 * The Class UriUtil.
 *
 * @author Mary Jane Buenaventura
 * @since 29/10/2016
 */
public class UriUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UriUtil.class);

	/** The Constant NAMES_PATTERN. */
	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");


	/**
	 * Instantiates a new uri util.
	 */
	private UriUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Expand uri component.
	 *
	 * @param source the source
	 * @param uriVariables the uri variables
	 * @return the string
	 */
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


	/**
	 * Gets the variable value as string.
	 *
	 * @param variableValue the variable value
	 * @return the variable value as string
	 */
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


	/**
	 * Gets the variable name.
	 *
	 * @param match the match
	 * @return the variable name
	 */
	private static String getVariableName(String match) {
		int colonIdx = match.indexOf(':');
		return (colonIdx != -1 ? match.substring(0, colonIdx) : match);
	}

}