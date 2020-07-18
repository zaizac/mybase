/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.rmq.constants;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 12, 2018
 */
public class QualifierConstants {

	private QualifierConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String SCOPE_PROTOTYPE = "prototype";

	public static final String AUTHORIZATION_DAO = "authDao";

	public static final String AUTHORIZATION_SVC = "authSvc";

	public static final String AUDIT_TRAIL_DAO = "auditTrailDao";

	public static final String AUDIT_TRAIL_SVC = "auditTrailSbv";
	
	public static final String ENDPOINT_CONFIG_DAO = "endpointConfigDao";
	
	public static final String ENDPOINT_CONFIG_SVC = "endpointConfigSvc";

}