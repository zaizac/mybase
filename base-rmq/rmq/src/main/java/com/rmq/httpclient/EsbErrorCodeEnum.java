/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.rmq.httpclient;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public enum EsbErrorCodeEnum {

	E503ESB000(503, "ESB service at {0} unreachable"),
	E503ESB001(503, "Service at {0} unreachable"),
	
	E400ESB001(400, "X-Message-Id Header is missing"),
	E400ESB002(400, "Authorization Header is missing"),
	E400ESB003(400, "Bad Request"),
	E400ESB004(400, "Invalid Request Body"),
	
	E404ESB005(404, "No Record Found")
	;
	
	private final int code;
	private final String message;
	
	EsbErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message =  message;
	}

	public static EsbErrorCodeEnum findByName(String name){
	    for(EsbErrorCodeEnum v : EsbErrorCodeEnum.values()){
	        if(v.name().equals(name)){
	            return v;
	        }
	    }
	    
	    return null;
	}
	
	public static int findInternalCode(String name) {
		for(EsbErrorCodeEnum v : EsbErrorCodeEnum.values()){
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
