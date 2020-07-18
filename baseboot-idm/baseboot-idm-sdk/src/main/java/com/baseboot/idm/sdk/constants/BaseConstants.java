/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.constants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class BaseConstants {

	private BaseConstants() {
		throw new IllegalStateException("Utility class");
	}


	public static final String EMPTY_STRING = "";

	public static final String SPACE = " ";

	public static final String COMMA = ",";

	public static final String USER_FIRST_TIME = "F";

	public static final String USER_ACTIVE = "A";

	public static final Integer ZERO = 0;

	public static final String DAY = "DAY";

	public static final String MONTH = "MONTH";

	public static final String YEAR = "YEAR";

	public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

	public static final String SIMPLE_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	public static final String DT_TIME_A = "HH:mm a";

	public static final String DT_TIME_S = "HH:mm:ss";

	public static final String DT_TIME_MS = "HH:mm:ss.s";

	public static final String DT_YYYY_MM_DD_DASH = "yyyy-MM-dd";

	public static final String DT_YYYY_MM_DD_DASH_TIME_A = "yyyy-MM-dd HH:mm a";

	public static final String DT_YYYY_MM_DD_DASH_TIME_S = "yyyy-MM-dd HH:mm:ss";

	public static final String DT_YYYY_MM_DD_DASH_TIME_MS = "yyyy-MM-dd HH:mm:ss.s";

	public static final String DT_YYYY_MM_DD_SLASH = "yyyy/MM/dd";

	public static final String DT_YYYY_MM_DD_SLASH_TIME_A = "yyyy/MM/dd HH:mm a";

	public static final String DT_YYYY_MM_DD_SLASH_TIME_S = "yyyy/MM/dd HH:mm:ss";

	public static final String DT_YYYY_MM_DD_SLASH_TIME_MS = "yyyy/MM/dd HH:mm:ss.s";

	public static final String DT_DD_MM_YYYY_DASH = "dd-MM-yyyy";

	public static final String DT_DD_MM_YYYY_DASH_TIME_A = "dd-MM-yyyy hh:mm a";

	public static final String DT_DD_MM_YYYY_DASH_TIME_S = "dd-MM-yyyy HH:mm:ss";

	public static final String DT_DD_MM_YYYY_DASH_TIME_MS = "dd-MM-yyyy HH:mm:ss.s";

	public static final String DT_DD_MM_YYYY_SLASH = "dd/MM/yyyy";

	public static final String DT_DD_MM_YYYY_SLASH_TIME_A = "dd/MM/yyyy hh:mm a";

	public static final String DT_DD_MM_YYYY_SLASH_TIME_S = "dd/MM/yyyy HH:mm:ss";

	public static final String DT_DD_MM_YYYY_SLASH_TIME_MS = "dd/MM/yyyy HH:mm:ss.s";

	public static final String STATUS_ACTIVE = "A";

	public static final String STATUS_INACTIVE = "I";

	public static final String HEADER_MESSAGE_ID = "X-Message-Id";

	public static final String HEADER_AUTHORIZATION = "Authorization";

	public static final String NEW_LINE = System.lineSeparator();

	public static final String LOG_SEPARATOR = "---------------------------------------------------------------------------------------";

	public static final int SYNC_INITIATE = 0;

	public static final int SYNC_INPROGRESS = 1;

	public static final int SYNC_COMPLETED = 2;

	public static final String MENU_LEVEL = "menuLevel";

	public static final String MENU_CODE = "menuCode";

	public static final String ROLE_CODE = "roleCode";

	public static final String AMOUNT_FORMAT = "#,###.00";

	public static final String AMOUNT_FORMAT_NO_DECIMAL = "#,###";

	public static final String BANK_REF_STATUS_TYPE = "BKPMTI";

	public static final String BANK_REF_STAT_TYPE = "BKPMT";

}
