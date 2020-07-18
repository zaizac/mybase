/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum ReportErrorCodeEnum {

	// 400 - Bad Request
	E400RPT001(400, "X-Message-Id Header is missing"),
	E400RPT002(400, "Authorization Header is missing"),
	E400RPT003(400, "Bad Request"),
	E400RPT004(400, "Missing Mandatory Field {0}"),

	// Error 401 - Unauthorized
	E401RPT001(401, "Unauthorized Access"),

	// 404 - Not Found
	I404RPT001(404, "No Record Found."),

	// 408 - Request Timeout
	E408RPT001(408, "Request timed out connecting to IDM after <response time>/<timeout duration>"),
	E408RPT002(408, "Request timed out connecting to Mongo after <response time>/<timeout duration>"),
	E408RPT003(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	// Error 409 - Conflict
	E409RPT001(409, "Record already exists"),

	// 500 - Internal Server Error
	E500RPT001(500, "Exception thrown from IDM service. {0}"),
	E500RPT002(500, "MongoException occured. {0}"),
	E500RPT003(500, "Exception thrown from Cache service. {0}"),
	E500RPT004(500, "Internal Server Error"),
	E500RPT005(500, "Failed to create record."),
	E500RPT006(500, "Failed to update record."),
	E500RPT007(500, "Failed to delete record."),
	E500RPT008(500, "Failed to retrieve record."),

	// 503 - Service Unavailable
	E503RPT000(503, "Report service at {0} unreachable"),
	E503RPT001(503, "IDM service at {0} unreachable"),
	E503RPT002(503, "Cache service at {0} unreachable"),
	E503RPT003(503, "Mongo service at {0} unreachable"),

	;

	private final int code;

	private final String message;


	ReportErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static ReportErrorCodeEnum findByName(String name) {
		for (ReportErrorCodeEnum v : ReportErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (ReportErrorCodeEnum v : ReportErrorCodeEnum.values()) {
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
