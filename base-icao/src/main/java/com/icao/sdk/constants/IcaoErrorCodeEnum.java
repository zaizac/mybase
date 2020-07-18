/**
 * Copyright 2019. Universal Recruitment Platform
 */
/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.icao.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum IcaoErrorCodeEnum {

	E503ICAOGEN(503, ""),
	E503ICAO000(503, "ICAO service at {0} unreachable"),
	E400ICAO911(400, "X-Message-Id Header is missing"),
	E400ICAO912(400, "Authorization Header is missing"),
	E400ICAO913(400, "Invalid Request Body"),
	I400ICAO152(400, "Client {0} not found."),
	E500ICAO903(500, "LDAPException occured. {0}"),
	E500ICAO904(500, "No client with requested id: {0}"),
	I404ICAO157(404, "User not found for user type {0}"),
	I404ICAO102(404, "User {0} not found"),
	I404ICAO103(404, "Mismatched password for user {0}"),
	I500ICAO105(500, "Failed to remove Token {0}"),
	E404ICAO140(404, "No Record Found"),
	I404ICAO130(404, "Profile for <user id> not found"),
	I200ICAO135(200, "Role {0} created successfully"),
	I409ICAO136(409, "{0} already exist"),
	I400ICAO137(400, "Missing value for compulsary fields {0} to create {1}"),
	I404ICAO153(404, "Creation failed."),
	I404ICAO151(404, "User {0} not found For Assign Role."),
	I404ICAO155(404, "Menu not found found"),
	I404ICAO148(404, "Role <role id> not found"),
	I404ICAO115(404, "Access Token {0} not found"),
	I200ICAO116(200, "Access Token {0} deleted successfully"),
	I404ICAO113(404, "Access Token not found"),
	I404ICAO107(404, "Refresh Token for Access Token {0} not found"),
	I400ICAO109(400, "Refresh Token {0} already expired"),
	I404ICAO111(404, "Acess Token {0} already expired"),
	I404ICAO160(404, "0 user group(s) found."),
	I409ICAO118(409, "{0} already exist"),
	I409ICAO119(409, "Inactive user status"),
	I400ICAO120(400, "Invalid value entered for {0} to create {1}"),
	I404ICAO128(404, "User {0} not found"),
	I400ICAO122(400, "Missing value for compulsary fields {0} to update {1}"),
	I404ICAO159(404, "Update failed."),
	I404ICAO158(404, "Invalid reuse of password present in password history."),
	E503ICAO901(503, "LDAP service at <LDAP ip address> unreachable"),
	E408ICAO902(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),
	I404ICAO161(404, "Update password failed. Invalid user branch {0}."),
	I409ICAO120(409, "User valid date has expired"),

	;

	private final int code;

	private final String message;


	IcaoErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static IcaoErrorCodeEnum findByName(String name) {
		for (IcaoErrorCodeEnum v : IcaoErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (IcaoErrorCodeEnum v : IcaoErrorCodeEnum.values()) {
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