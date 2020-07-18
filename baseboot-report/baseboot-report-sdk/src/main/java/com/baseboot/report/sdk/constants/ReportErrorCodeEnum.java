/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum ReportErrorCodeEnum {

	// 400
	E400RPT011(400, "X-Message-Id Header is missing"),
	E400RPT012(400, "Authorization Header is missing"),
	E400RPT013(400, "Invalid Request Body"),
	E400RPT014(400, "Invalid Search Type Request"),
	E400RPT015(400, "From date (or) To date can not be empty"),
	E400RPT016(400, "No Data Found, from Related Module"),
	E400RPT018(400, "Bad Request,Invalid Report Type"),

	I404RPT019(400, "Documents Not Available Please update documents in Company Profile"),

	// 404
	E404RPT007(404, "Cache service at {0} unreachable"),
	I404RPT103(404, "User {0} Not Found."),
	I404RPT003(404, "No Record Found."),

	// 408
	E408RPT002(408, "Request timed out connecting to IDM after <response time>/<timeout duration>"),
	E408RPT005(408, "Request timed out connecting to Mongo after <response time>/<timeout duration>"),
	E408RPT008(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	// 500
	E500RPT003(500, "Exception thrown from IDM service. {0}"),
	E500RPT006(500, "MongoException occured. {0}"),
	E500RPT009(500, "Exception thrown from Cache service. {0}"),
	E400RPT017(500, "Internal Server Error"),

	// 503
	E503RPT000(503, "Report service at {0} unreachable"),
	E503RPT001(503, "IDM service at {0} unreachable"),
	E503RPT004(503, "Mongo service at {0} unreachable"),

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
