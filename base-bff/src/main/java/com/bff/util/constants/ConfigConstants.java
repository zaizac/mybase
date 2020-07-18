/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.util.constants;


import java.io.File;

import com.util.constants.BaseConfigConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 17, 2018
 */
public class ConfigConstants {

	private ConfigConstants() {
		throw new IllegalStateException(ConfigConstants.class.getName());
	}


	public static final String BASE_PACKAGE = "com.bff";

	public static final String CACHE_JAVA_FILE = "T(" + BASE_PACKAGE + ".bff.util.constants.CacheConstants)";

	public static final String PATH_PROJ_CONFIG = "base.config.path";

	public static final String PROPERTY_FILENAME = "base-bff";

	public static final String FILE_SYS_RESOURCE = File.separator + PROPERTY_FILENAME
			+ BaseConfigConstants.PROPERTIES_EXT;

	public static final String PROPERTY_CLASSPATH = BaseConfigConstants.CLASSPATH_PFX + PROPERTY_FILENAME;

	public static final String SVC_BE_URL = "be.service.url";

	public static final String SVC_BE_TIMEOUT = "be.service.timeout";

	public static final String FPX_BEN_NM = "fpx.ben.name";

	public static final String FPX_BEN_BANK_NM = "fpx.ben.bank.name";

	public static final String FPX_BEN_ACCNT_NM = "fpx.ben.account.name";

	public static final String FPX_BEN_ACCNT_NO = "fpx.ben.account.no";

	public static final String FPX_B2C_MAX_AMOUNT = "fpx.b2c.max.amount";

	public static final String FPX_BESTPAY_REDIRECT_URL = "fpx.bestpay.redirect.url";

	public static final String FPX_BESTPAY_SELLER_ID_EPAY = "fpx.syner.seller.id.epay";

	public static final String FPX_BESTPAY_SELLER_ID_EVDR = "fpx.syner.seller.id.evdr";

	public static final String FPX_BESTPAY_EX_ID = "fpx.syner.seller.ex.id";

	public static final String SVC_REPORT_URL = "rpt.service.url";

	public static final String SVC_REPORT_TIMEOUT = "rpt.service.timeout";
}
