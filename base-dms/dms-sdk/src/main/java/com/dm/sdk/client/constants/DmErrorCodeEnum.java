/**
 * Copyright 2019. 
 */
package com.dm.sdk.client.constants;


/**
 * The Enum DmErrorCodeEnum.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum DmErrorCodeEnum {

	/** The e400domgen. */
	E400DOMGEN(400, "{0}"),
	
	/** The e400dom911. */
	E400DOM911(400, "X-Message-Id Header is missing"),
	
	/** The e400dom912. */
	E400DOM912(400, "Authorization Header is missing"),
	
	/** The e400dom913. */
	E400DOM913(400, "Required parameter {0} is not present"),

	/** The e503domgen. */
	E503DOMGEN(503, "{0}"),
	
	/** The e503dom000. */
	E503DOM000(503, "DM service at {0} unreachable"),

	/** The e500domgen. */
	E500DOMGEN(500, "{0}"),
	
	/** The e500dom001. */
	E500DOM001(500, "Upload Failed for {0}"),
	
	/** The e500dom002. */
	E500DOM002(500, "Download Failed for {0} - {1}"),
	
	/** The e500dom003. */
	E500DOM003(500, "Upload Failed - {0}: {1}"),
	
	/** The e500dom004. */
	E500DOM004(500, "Invalid Doc Mgt Id {0}"),

	/** The e404domgen. */
	E404DOMGEN(404, "{0}"),
	
	/** The e404dom001. */
	E404DOM001(404, "No Record Found"),

	/** The e405domgen. */
	E405DOMGEN(405, "{0}"),
	
	/** The e408domgen. */
	E408DOMGEN(408, "{0}"),
	
	/** The e415domgen. */
	E415DOMGEN(415, "{0}"),

	;

	/** The code. */
	private final int code;

	/** The message. */
	private final String message;


	/**
	 * Instantiates a new dm error code enum.
	 *
	 * @param code the code
	 * @param message the message
	 */
	DmErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the dm error code enum
	 */
	public static DmErrorCodeEnum findByName(String name) {
		for (DmErrorCodeEnum v : DmErrorCodeEnum.values()) {
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
		for (DmErrorCodeEnum v : DmErrorCodeEnum.values()) {
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
