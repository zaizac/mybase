/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.report.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class ReportUrlConstants {

	private ReportUrlConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String SERVICE_CHECK = "/api/v1/serviceCheck";

	public static final String REPORT = "/reports";

	public static final String REPORT_CACHE_EVICT = REPORT + "/cacheEvict";

	public static final String WORKER_REPORT = REPORT + "/wrkrRpt";

}