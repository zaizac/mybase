/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.core.exception;


import java.util.Set;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class UnknownRequestParamException extends IllegalArgumentException {

	private static final long serialVersionUID = 1124224664362878115L;


	public UnknownRequestParamException(String param, Set<String> allowedParams) {
		super("Parameter '" + param + "' is not supported. Supported parameters " + "are " + allowedParams + ".");
	}

}
