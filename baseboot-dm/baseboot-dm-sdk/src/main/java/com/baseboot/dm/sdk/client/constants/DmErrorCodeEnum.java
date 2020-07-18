/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum DmErrorCodeEnum {

	E400DOMGEN(400, "{0}"),
	E400DOM911(400, "X-Message-Id Header is missing"),
	E400DOM912(400, "Authorization Header is missing"),
	E400DOM913(400, "Required parameter {0} is not present"),

	E503DOMGEN(503, "{0}"),
	E503DOM000(503, "DM service at {0} unreachable"),

	E500DOMGEN(500, "{0}"),
	E500DOM001(500, "Upload Failed for {0}"),
	E500DOM002(500, "Download Failed for {0} - {1}"),
	E500DOM003(500, "Upload Failed - {0}: {1}"),

	E404DOMGEN(404, "{0}"),
	E404DOM001(404, "No Record Found"),

	E405DOMGEN(405, "{0}"),
	E408DOMGEN(408, "{0}"),
	E415DOMGEN(415, "{0}"),

	;

	private final int code;

	private final String message;


	DmErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static DmErrorCodeEnum findByName(String name) {
		for (DmErrorCodeEnum v : DmErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (DmErrorCodeEnum v : DmErrorCodeEnum.values()) {
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
