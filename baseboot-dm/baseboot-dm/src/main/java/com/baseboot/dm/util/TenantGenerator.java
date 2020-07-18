/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.dm.util;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class TenantGenerator {

	public static String tenant = "dm_default";

	public static String audit = "dm_audit_trail";


	public static final String tenant() {
		return tenant;
	}


	public static final String audit() {
		return audit;
	}
}