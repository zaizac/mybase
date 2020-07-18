/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.sgw.sdk.client.constants;


/**
 * @author Mary Jane Buenaventura
 * @since 11/12/2014
 */
public class BaseConstants {

	private BaseConstants() {
		throw new IllegalStateException("Constants class");
	}


	public static final String EMPTY_STRING = "";

	public static final String SPACE = " ";

	public static final String COMMA = ",";

	public static final String SLASH = "/";

	public static final String DASH = "-";

	public static final Integer ZERO = 0;

	public static final String DAY = "DAY";

	public static final String MONTH = "MONTH";

	public static final String YEAR = "YEAR";

	public static final String FIRST_TIME_PWD = "F";

	/* Date & Time Constants */
	public static final String SIMPLE_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	public static final String DT_FORMAT = "yyyyMMdd";

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

	protected static final String HEADER_MESSAGE_ID = "X-Message-Id";

	protected static final String HEADER_AUTHORIZATION = "Authorization";

	protected static final String PERMISSION_CODE = "txnNo";

}
