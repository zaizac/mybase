/**
 * Copyright 2019
 */
package com.be.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2016
 */
public enum BeErrorCodeEnum {

	E200C000(200, "Successful"),

	E400C913(400, "Invalid Request Body"),
	E400C912(400, "Invalid Criteria"),

	E400C001(400, "X-Message-Id Header is missing"),
	E400C002(400, "Authorization Header is missing"),
	E400C003(400, "Invalid Request Body"),
	E400C004(400, "Bad Request"),
	E400C005(400, "{0} is required."),
	E400C006(400, "Missing value for compulsary fields {0}"),
	E400C007(400, "Invalid transaction."),

	I404C001(404, "The record doesn't exists {0}"),
	I404C002(404, "Document Management Id {0} not found."),

	E408C001(408, "Request timed out connecting to Mongo after <response time>/<timeout duration>"),
	E408C002(408, "Request timed out connecting to IDM after <response time>/<timeout duration>"),
	E408C003(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),

	I409C001(409, "The record already exists"),
	I409C002(409, "Payment Transaction already exists."),

	E500C001(500, "Failed to create record."),
	E500C002(500, "Failed to update record."),
	E500C003(500, "Failed to delete record."),
	E500C004(500, "Failed to retrieve record."),
	E500C005(503, "IO mark/reset not supported"),

	E500C006(500, "Exception thrown from Workflow service. {0}"),
	E500C007(500, "Exception thrown from IDM service. {0}"),
	E500C008(500, "Failed to create Workflow record."),
	E500C009(500, "Exception thrown from Notification service. {0}"),
	E500C010(500, "Exception thrown from DM service. {0}"),

	E503C000(503, "Bio Registration BE service at {0} unreachable"),
	E503C001(503, "IDM service at {0} unreachable"),
	E503C002(503, "Cache service at {0} unreachable"),
	E503C003(503, "SST service at {0} unreachable"),

	E503BE001(503, "BE service at {0} unreachable"),
	I404EQM957(404, "Registration No {0} already exists"),
	E503C004(503, "Mongo service at {0} unreachable"),
	E404EMB015(404, "Attestation Detail for Quota Reference No {0} not found."),
	E404EMB016(404, "Attestation Reference No {0} not found."),
	E404EMB017(404, "Document for Document Reference No {0} not found."),
	E404EMB019(404, "Company Registration No {0} not found."),
	E404EMB020(404, "Company Address ({0}) for Company Registration No {1} not found."),
	E404EMB022(404, "Owner Id {0} for Company Registration No {1} not found."),
	E404EMB023(404, "Embassy Profile for country {0} not found."),
	E400EMB024(400, "Demand Letter for Quota Reference No {0} already exist."),
	E500EMB025(500, "Update Workflow Task Id {0} failed."),
	E404EMB026(404, "Demand Letter for Quota Reference No {0} not found."),
	E404EMB027(404, "Workflow Task Id {0} not found."),
	E400EMB028(400, "Attestation Detail for Quota Reference No {0} list empty."),
	E400EMB029(400, "Supporting Documents for Quota Reference No {0} list empty."),
	E404EMB030(404, "Attestation Detail for Quota Reference No {0} and Agent Id {1} not found."),
	E404EMB031(404, "Agent Detail for Agent Id {0} not found."),
	E400EMB032(400, "Quota Reference No list empty."),
	E404CMN937(404, "Sector Type {0} not found"),
	E404EMB033(404, "Document for Document Reference No {0} and Document Id {1} not found."),
	I400EQM958(400, "Registration No {0} Not Found."),
	E404EMB021(404, "Document Management Id {0} not found."),
	E404EQM144(404, "{0} not found for {1}"),
	E404EQM145(400, "Failed to Create/Update {0} for {1} {2}"),
	E404EQM146(404, "No Receipt No. Found"),
	I404EQM111(405, "Can not save the record"),
	E404EMB037(404, "Permission Reference No {0} not found."),
	I400EQM134(400, "Failed to Create/Update Quota App for Quota Ref No {0}");

	public final int code;

	public final String message;


	BeErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static BeErrorCodeEnum findByName(String name) {
		for (BeErrorCodeEnum v : BeErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (BeErrorCodeEnum v : BeErrorCodeEnum.values()) {
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
