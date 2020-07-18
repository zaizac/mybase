/**
 * Copyright 2019. 
 */
package com.idm.sdk.constants;

/**
 * @author Mary Jane Buenaventura
 * @since Sep 3, 2015
 */
public enum ResponseCodeEnum {

	E503IDM000(503, "IDM service at {0} unreachable"),
	E503IDM901(503, "LDAP service at <LDAP ip address> unreachable"),
	E408IDM902(408, "Request timed out connecting to LDAP after <response time>/<timeout duration>"),
	E500IDM903(500, "LDAPException occured. {0}"),
	E503IDM904(503, "MySQL service at <MySQL ip address> unreachable"),
	E408IDM905(408, "Request timed out connecting to MySQL after <response time>/<timeout duration>"),
	E500IDM906(500, "SQLException occured. <SQL Exception Error Message>"),
	E404IDM907(404, "Cache service at <Cache server ip address> unreachable"),
	E408IDM908(408, "Request timed out connecting to Cache service after <response time>/<timeout duration>"),
	E500IDM909(500, "Exception thrown from Cache service. <Exception Error Message>"),
	E400IDM911(400, "X-Message-Id Header is missing"),
	E400IDM912(400, "Authorization Header is missing"),
	E400IDM913(400, "Invalid Request Body"),
	
	I200IDM101(200, "Login successful for user {0}"),
	I404IDM102(404, "User {0} not found"),
	I404IDM103(404, "Mismatched password for user {0}"),
	I200IDM104(200, "Logout successful for user {0}"),
	I500IDM105(500, "Failed to remove Token {0}"),
	I200IDM106(200, "Access Token {0} generated successfully"),
	I404IDM107(404, "Refresh Token for Access Token {0} not found"),
	I400IDM108(400, "Invalid Refresh Token {0}"),
	I400IDM109(400, "Refresh Token {0} already expired"),
	I200IDM110(200, "Acess Token {0} is valid"),
	I404IDM111(404, "Acess Token {0} already expired"),
	I200IDM112(200, "Successfully fetch {0}"),
	I404IDM113(404, "Access Token not found"),
	I200IDM114(400, "Access Token {0} found"),
	I404IDM115(404, "Access Token {0} not found"),
	I200IDM116(200, "Access Token {0} deleted successfully"),
	I200IDM117(200, "User <user id> created successfully"),
	I409IDM118(409, "{0} already exist"),
	I400IDM119(400, "Missing value for compulsary fields <list of fields> to create <user id>"),
	I400IDM120(400, "Invalid value entered for {0} to create {1}"),
	I200IDM121(200, "User <user id> updated successfully"),
	I400IDM122(400, "Missing value for compulsary fields {0} to update {1}"),
	I400IDM123(400, "Invalid value entered for <list of fields and existing value> to update <user id>"),
	I200IDM124(200, "User <user id> deleted successfully"),
	I200IDM125(200, "<no of users> sucessfully fetched"),
	I404IDM126(404, "0 user(s) found"),
	I200IDM127(200, "<no of users> sucessfully fetched"),
	I404IDM128(404, "User {0} not found"),
	I200IDM129(200, "User <user id>'s profile updated sucessfully"),
	I404IDM130(404, "Profile for <user id> not found"),
	I200IDM131(200, "<no of users>s' profile sucessfully fetched"),
	I404IDM132(404, "0 profile(s) found"),
	I200IDM133(200, "Successfully fetch Profile for User <user id>"),
	I404IDM134(404, "Profile for <user id> not found"),
	I200IDM135(200, "Role {0} created successfully"),
	I409IDM136(409, "{0} already exist"),
	I400IDM137(400, "Missing value for compulsary fields {0} to create {1}"),
	I400IDM138(400, "Invalid value entered for <list of fields and existing value> to create <role name>"),
	I200IDM139(200, "Role <role id> updated successfully"),
	I400IDM140(400, "Missing value for compulsary fields <list of fields> to update <role id>"),
	I400IDM141(400, "Invalid value entered for <list of fields and existing value> to update <role id>"),
	I200IDM142(200, "Role <role id> deleted successfully"),
	I200IDM143(200, "<no of roles> sucessfully fetched"),
	I404IDM144(404, "0 role(s) found"),
	I200IDM145(200, "Role for <role id> not found"),
	I404IDM146(404, "0 role(s) found"),
	I200IDM147(200, "<no of permissions> succesfully added to Role <role id>"),
	I404IDM148(404, "Role <role id> not found"),
	I200IDM149(200, "{0} succesfully added to Role {1}"),
	I404IDM150(404, "Role <role id> not found"),
	I404IDM151(404, "User {0} not found For Assign Role."),
	I404IDM152(404, "Client {0} not found."),
	I404IDM153(404, "Creation failed."),
	I200IDM154(200, "Role Assignment Successfull."),
	I404IDM155(404, "Menu not found found"),
	I200IDM156(200, "Menu Assignment Successfull."),
	I404IDM157(404, "User not found for user type {0}"),
	I404IDM158(404, "Invalid reuse of password present in password history."),
	I404IDM159(404, "Update failed."),
	I404IDM160(404, "0 user group(s) found.");
	
	private final int code;
	private final String message;
	
	ResponseCodeEnum(int code, String message) {
		this.code = code;
		this.message =  message;
	}

	public static ResponseCodeEnum findByName(String name){
	    for(ResponseCodeEnum v : ResponseCodeEnum.values()){
	        if(v.name().equals(name)){
	            return v;
	        }
	    }
	    
	    return null;
	}
	
	public static int findInternalCode(String name) {
		for(ResponseCodeEnum v : ResponseCodeEnum.values()){
	        if(v.name().equals(name)){
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