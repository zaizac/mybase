/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.rmq.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public enum SgwErrorCodeEnum {

	E200SGW000(200, "Successful"),

	// Error 400 - Bad Request
	E400SGW001(400, "X-Message-Id Header is missing"),
	E400SGW002(400, "Authorization Header is missing"),
	E400SGW003(400, "Bad Request"),
	E400SGW004(400, "Missing Mandatory Field {0}"),
	E400SGW005(400, "Invalid Security Hashing."),

	// Error 401 - Unauthorized
	E401SGW001(401, "Unauthorized Access"),

	// Error 404 - Not Found
	E404SGW001(404, "No Record Found"),

	// Error 408 - Request Timeout
	E408SGW001(408, "Request timed out connecting to IDM after <response time>/<timeout duration>"),
	E408SGW002(408, "Request timed out connecting to Mongo after <response time>/<timeout duration>"),
	E408SGW003(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	// Error 409 - Conflict
	E409SGW001(409, "Record already exists"),

	// Error 500 - Internal Server Error
	E500SGW001(500, "Failed to create record."),
	E500SGW002(500, "Failed to update record."),
	E500SGW003(500, "Failed to delete record."),
	E500SGW004(500, "Failed to retrieve record."),

	// Service error
	E500SGW010(500, "Exception thrown from DM service. {0}"),

	// Error 503 - Service Unavailable
	E503SGW000(503, "Service Unreachable"),
	E503SGW001(503, "System unavailable. Please contact administrator."),;

	private final int code;

	private final String message;


	SgwErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static SgwErrorCodeEnum findByName(String name) {
		for (SgwErrorCodeEnum v : SgwErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (SgwErrorCodeEnum v : SgwErrorCodeEnum.values()) {
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