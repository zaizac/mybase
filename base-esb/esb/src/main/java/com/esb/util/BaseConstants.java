/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.esb.util;

/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class BaseConstants {

	public static final String EMPTY_STRING 				= "";
	public static final String SPACE 						= " ";
	public static final String COMMA 						= ",";
	
	public static final Integer ZERO 						= 0;
	
	public static final String DAY							= "DAY";
	public static final String MONTH						= "MONTH";
	public static final String YEAR							= "YEAR";
	
	public static final String FIRST_TIME_PWD				= "F";
	
	/* Date & Time Constants */
    public static final String SIMPLE_TIMESTAMP_FORMAT 		= "yyyyMMddHHmmss";
    public static final String DT_FORMAT			 		= "yyyyMMdd";
    public static final String DT_TIME_a	 				= "HH:mm a";
    public static final String DT_TIME_s	 				= "HH:mm:ss";
    public static final String DT_TIME_ms	 				= "HH:mm:ss.s";
    public static final String DT_YYYY_MM_DD_Dash		 	= "yyyy-MM-dd";
    public static final String DT_YYYY_MM_DD_Dash_TIME_a 	= "yyyy-MM-dd HH:mm a";
    public static final String DT_YYYY_MM_DD_Dash_TIME_s 	= "yyyy-MM-dd HH:mm:ss";
    public static final String DT_YYYY_MM_DD_Dash_TIME_ms 	= "yyyy-MM-dd HH:mm:ss.s";
    public static final String DT_YYYY_MM_DD_Slash			= "yyyy/MM/dd";
    public static final String DT_YYYY_MM_DD_Slash_TIME_a	= "yyyy/MM/dd HH:mm a";
    public static final String DT_YYYY_MM_DD_Slash_TIME_s	= "yyyy/MM/dd HH:mm:ss";
    public static final String DT_YYYY_MM_DD_Slash_TIME_ms	= "yyyy/MM/dd HH:mm:ss.s";
    public static final String DT_DD_MM_YYYY_Dash			= "dd-MM-yyyy";
	public static final String DT_DD_MM_YYYY_Dash_TIME_a 	= "dd-MM-yyyy hh:mm a";
	public static final String DT_DD_MM_YYYY_Dash_TIME_s	= "dd-MM-yyyy HH:mm:ss";
	public static final String DT_DD_MM_YYYY_Dash_TIME_ms	= "dd-MM-yyyy HH:mm:ss.s";
    public static final String DT_DD_MM_YYYY_Slash			= "dd/MM/yyyy";
	public static final String DT_DD_MM_YYYY_Slash_TIME_a 	= "dd/MM/yyyy hh:mm a";
	public static final String DT_DD_MM_YYYY_Slash_TIME_s	= "dd/MM/yyyy HH:mm:ss";
	public static final String DT_DD_MM_YYYY_Slash_TIME_ms	= "dd/MM/yyyy HH:mm:ss.s";
	public static final String DT_DD_MMMM_YYYY				= "dd MMMM yyyy";
	public static final String DT_DD_MMMM_YYYY_TIME_a		= "dd MMMM yyyy hh:mm a";
	
	protected static final String HEADER_MESSAGE_ID 		= "X-Message-Id";
	protected static final String HEADER_AUTHORIZATION 		= "Authorization";
	protected static final String PERMISSION_CODE 			= "txnNo";
	
	public static final int SYNC_INITIATE					= 0;
	public static final int SYNC_INPROGRESS					= 1;
	public static final int SYNC_COMPLETED					= 2;
	
}
