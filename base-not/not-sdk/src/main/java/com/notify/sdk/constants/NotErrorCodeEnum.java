/**
 * Copyright 2019. 
 */
package com.notify.sdk.constants;


/**
 * The Enum NotErrorCodeEnum.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum NotErrorCodeEnum {

	/** The e400not001. */
	// 400 Error
	E400NOT001(400, "X-Message-Id Header is missing"),

	/** The e400not002. */
	E400NOT002(400, "Authorization Header is missing"),

	/** The e400not003. */
	E400NOT003(400, "Invalid Request"),

	/** The e404not001. */
	// 404 Error
	E404NOT001(404, "Cache service at <Cache server ip address> unreachable"),

	/** The e404not002. */
	E404NOT002(404, "Notification Creation fails."),

	/** The e404not003. */
	E404NOT003(404, "Email Template not found."),

	/** The e404not004. */
	E404NOT004(404, "Email Template Creation fails."),

	/** The e404not005. */
	E404NOT005(404, "No record found."),

	/** The e408not001. */
	// 408 Error
	E408NOT001(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),

	/** The e408not002. */
	E408NOT002(408, "Request timed out connecting to MySQL after <response time>/<timeout duration>"),

	/** The e408not003. */
	E408NOT003(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	/** The e500not001. */
	// 500 Error
	E500NOT001(500, "LDAPException occured. {0}"),

	/** The e500not002. */
	E500NOT002(500, "SQLException occured. <SQL Exception Error Message>"),

	/** The e500not003. */
	E500NOT003(500, "Exception thrown from Cache service. <Exception Error Message>"),

	/** The e503not000. */
	// 503 Error
	E503NOT000(503, "NOT service at {0} unreachable"),

	/** The e503not002. */
	E503NOT002(503, "LDAP service at <LDAP ip address> unreachable"),

	/** The e503not003. */
	E503NOT003(503, "MySQL service at <MySQL ip address> unreachable"),

	/** The e503not004. */
	E503NOT004(503, "SMS service is turned OFF."),
	
	E503NOT005(503, "FCM Service is not available."),
	
	;

	/** The code. */
	private final int code;

	/** The message. */
	private final String message;


	/**
	 * Instantiates a new not error code enum.
	 *
	 * @param code
	 *             the code
	 * @param message
	 *             the message
	 */
	NotErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	/**
	 * Find by name.
	 *
	 * @param name
	 *             the name
	 * @return the not error code enum
	 */
	public static NotErrorCodeEnum findByName(String name) {
		for (NotErrorCodeEnum v : NotErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	/**
	 * Find internal code.
	 *
	 * @param name
	 *             the name
	 * @return the int
	 */
	public static int findInternalCode(String name) {
		for (NotErrorCodeEnum v : NotErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v.getCode();
			}
		}

		return 0;
	}


	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

}