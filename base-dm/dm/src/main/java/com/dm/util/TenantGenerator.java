/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.util;


/**
 * The Class TenantGenerator.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public final class TenantGenerator {

	/**
	 * Instantiates a new tenant generator.
	 */
	private TenantGenerator() {
		throw new IllegalStateException("TenantGenerator Utility class");
	}


	/** The Constant TENANT. */
	public static final String TENANT = "dm_default";

	/** The Constant AUDIT. */
	public static final String AUDIT = "dm_audit_trail";

}