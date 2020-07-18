/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public enum IdmErrorCodeEnum {

	I200C000IDM(200, "SUCCESS"),
	E503IDMGEN(503, ""),
	E503IDM000(503, "IDM service at {0} unreachable"),
	E404IDM001(404, "IDM service at {0} does not exists"),
	E400IDM911(400, "X-Message-Id Header is missing"),
	E400IDM912(400, "Authorization Header is missing"),
	E400IDM913(400, "Invalid Request Body"),
	I400IDM152(400, "Client {0} not found."),
	E500IDM903(500, "LDAPException occured. {0}"),
	E500IDM904(500, "No client with requested id: {0}"),
	I404IDM157(404, "User not found for user type {0}"),
	I404IDM102(404, "User {0} not found"),
	I404IDM103(404, "Mismatched password for user {0}"),
	I500IDM105(500, "Failed to remove Token {0}"),
	E404IDM140(404, "No Record Found"),
	I404IDM130(404, "Profile for <user id> not found"),
	I200IDM135(200, "Role {0} created successfully"),
	I409IDM136(409, "{0} already exist"),
	I400IDM137(400, "Missing value for compulsary fields {0} to create {1}"),
	I404IDM153(404, "Creation failed."),
	I404IDM151(404, "User {0} not found For Assign Role."),
	I404IDM155(404, "Menu not found found"),
	I404IDM148(404, "Role <role id> not found"),
	I404IDM115(404, "Access Token {0} not found"),
	I200IDM116(200, "Access Token {0} deleted successfully"),
	I404IDM113(404, "Access Token not found"),
	I404IDM114(404, "Missing Social Login Access Token"),
	I404IDM107(404, "Refresh Token for Access Token {0} not found"),
	I400IDM109(400, "Refresh Token {0} already expired"),
	I404IDM111(404, "Acess Token {0} already expired"),
	I404IDM160(404, "0 user group(s) found."),
	I409IDM118(409, "{0} already exist"),
	I409IDM119(409, "Inactive user status"),
	I400IDM120(400, "Invalid value entered for {0} to create {1}"),
	I404IDM128(404, "User {0} not found"),
	I400IDM122(400, "Missing value for compulsary fields {0} to update {1}"),
	I404IDM159(404, "Update failed."),
	I404IDM158(404, "Invalid reuse of password present in password history."),
	E503IDM901(503, "LDAP service at <LDAP ip address> unreachable"),
	E408IDM902(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),
	I404IDM161(404, "Update password failed. Invalid user branch {0}."),
	I409IDM120(409, "User valid date has expired"),
	I409IDM121(409, "Pending Activation"),
	E400IDM122(400, "User creation failed in LDAP."),
	E400IDM123(400, "Invalid User Type: {0}"),;

	private final int code;

	private final String message;


	IdmErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public static IdmErrorCodeEnum findByName(String name) {
		for (IdmErrorCodeEnum v : IdmErrorCodeEnum.values()) {
			if (v.name().equals(name)) {
				return v;
			}
		}

		return null;
	}


	public static int findInternalCode(String name) {
		for (IdmErrorCodeEnum v : IdmErrorCodeEnum.values()) {
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