/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Sep 10, 2015
 */
public enum WfwErrorCodeEnum {

	E503WFW000(503, "Workflow service at {0} unreachable"),
	E500WFW004(504, "Exception thrown from Workflow service. {0}"),
	E400WFW011(400, "X-Message-Id Header is missing"),
	E400WFW012(400, "Authorization Header is missing"),
	E400WFW013(400, "Invalid Request Body"),
	E400WFW001(400, "Invalid operation code"),
	E400WFW002(400, "Invalid process : {0}"),

	// wrong parameter
	E400WFWWP001(400, "Date From cann't be null."),
	E400WFWWP002(400, "Date To cann't be null."),
	E400WFWWP003(400, "Date From and Date To cann't be null."),

	I404WFW001(404, "Task not found : {0}"),
	I404WFW002(404, "No records found"),

	E500WFW001(500, "Failed to create/update record."),

	;

	private final int code;

	private final String message;


	WfwErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static WfwErrorCodeEnum findByName(String name) {
		for (WfwErrorCodeEnum v : WfwErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (WfwErrorCodeEnum v : WfwErrorCodeEnum.values()) {
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
