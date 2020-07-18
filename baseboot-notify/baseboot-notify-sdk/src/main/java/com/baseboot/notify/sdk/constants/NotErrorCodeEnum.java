/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum NotErrorCodeEnum {

	// 400 Error
	E400NOT001(400, "X-Message-Id Header is missing"),
	E400NOT002(400, "Authorization Header is missing"),
	E400EQM003(400, "Invalid Request Body"),

	// 404 Error
	E404NOT001(404, "Cache service at <Cache server ip address> unreachable"),
	E404NOT002(404, "Notification Creation fails."),
	E404NOT003(404, "Email Template does not exists."),
	E404NOT004(404, "Email Template Creation fails."),

	// 408 Error
	E408NOT001(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),
	E408NOT002(408, "Request timed out connecting to MySQL after <response time>/<timeout duration>"),
	E408NOT003(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	// 500 Error
	E500NOT001(500, "LDAPException occured. {0}"),
	E500NOT002(500, "SQLException occured. <SQL Exception Error Message>"),
	E500NOT003(500, "Exception thrown from Cache service. <Exception Error Message>"),

	// 503 Error
	E503NOT000(503, "NOT service at {0} unreachable"),
	E503NOT002(503, "LDAP service at <LDAP ip address> unreachable"),
	E503NOT003(503, "MySQL service at <MySQL ip address> unreachable"),
	E503NOT004(503, "SMS service is turned OFF."),;

	private final int code;

	private final String message;


	NotErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static NotErrorCodeEnum findByName(String name) {
		for (NotErrorCodeEnum v : NotErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (NotErrorCodeEnum v : NotErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v.getCode();
			}
		}

		return 0;
	}


	public String getMessage() {
		return message;
	}


	public int getCode() {
		return code;
	}

}