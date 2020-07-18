/**
 * Copyright 2019. 
 */
package com.util.constants;


/**
 * The Enum ResponseCodeEnum.
 *
 * @author Mary Jane Buenaventura
 * @since Sep 3, 2015
 */
public enum ResponseCodeEnum {

	/** The e503c000. */
	E503C000(503, "{0} service at {1} unreachable"),
	
	/** The e503idm901. */
	E503IDM901(503, "LDAP service at <LDAP ip address> unreachable"),
	
	/** The e408idm902. */
	E408IDM902(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),
	
	/** The e500idm903. */
	E500IDM903(500, "LDAPException occured. {0}"),
	
	/** The e503idm904. */
	E503IDM904(503, "MySQL service at <MySQL ip address> unreachable"),
	
	/** The e408idm905. */
	E408IDM905(408, "Request timed out connecting to MySQL after <response time>/<timeout duration>"),
	
	/** The e500idm906. */
	E500IDM906(500, "SQLException occured. <SQL Exception Error Message>"),
	
	/** The e404idm907. */
	E404IDM907(404, "Cache service at <Cache server ip address> unreachable"),
	
	/** The e408idm908. */
	E408IDM908(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),
	
	/** The e500idm909. */
	E500IDM909(500, "Exception thrown from Cache service. <Exception Error Message>"),
	
	/** The e400idm911. */
	E400IDM911(400, "X-Message-Id Header is missing"),
	
	/** The e400idm912. */
	E400IDM912(400, "Authorization Header is missing"),
	
	/** The e400idm913. */
	E400IDM913(400, "Invalid Request Body"),

	;

	/** The code. */
	private final int code;

	/** The message. */
	private final String message;


	/**
	 * Instantiates a new response code enum.
	 *
	 * @param code the code
	 * @param message the message
	 */
	ResponseCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the response code enum
	 */
	public static ResponseCodeEnum findByName(String name) {
		for (ResponseCodeEnum v : ResponseCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	/**
	 * Find internal code.
	 *
	 * @param name the name
	 * @return the int
	 */
	public static int findInternalCode(String name) {
		for (ResponseCodeEnum v : ResponseCodeEnum.values()) {
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