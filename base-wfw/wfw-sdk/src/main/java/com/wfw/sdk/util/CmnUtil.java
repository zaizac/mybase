/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.sdk.util;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wfw.sdk.constants.CmnConstants;


/**
 * @author Kamruzzaman
 */
public final class CmnUtil {

	private CmnUtil() {
		throw new IllegalStateException("CmnUtil Utility class");
	}


	/***
	 * This method will return a trim string, if object passing is null it will
	 * return EMPTY STRING("")
	 *
	 * @param obj
	 *             Object that are passed in
	 * @return a string representation of the object
	 * @exception Exception
	 *                 if have error occur
	 */
	public static String getStr(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	/**
	 * This method will return a trim string, if object passing is null it will
	 * return null. This function needed as JMS functions need to maintain the
	 * null values rather than empty string
	 *
	 * @param obj
	 *             Object that are passed in
	 * @return a string representation of the object
	 */
	public static String getStrWithNull(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		} else {
			return null;
		}
	}


	/***
	 * This method will return a trim UPPERCASE string, if object passing is
	 * null it will return EMPTY STRING("")
	 *
	 * @param obj
	 *             Object that are passed in
	 * @return a string representation of the object
	 * @exception Exception
	 *                 if have error occur
	 */
	public static String getStrUpper(Object obj) {
		if (obj != null) {
			return obj.toString().trim().toUpperCase();
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	/**
	 * This method is same with getStrWithNull() The returned string will be
	 * either in UPPERCASE or null values
	 *
	 * @param obj
	 *             Object that are passed in
	 * @return a string representation of the object with UPPERCASE
	 */
	public static String getStrUpperWithNull(Object obj) {
		if (obj != null && !getStr(obj).equalsIgnoreCase("NULL")) {
			return obj.toString().trim().toUpperCase();
		} else {
			return null;
		}
	}


	public static String getStrLower(Object obj) {
		if (obj != null) {
			return obj.toString().trim().toLowerCase();
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	/**
	 * This method will return string to Title Case
	 *
	 * @param obj
	 * @return String in Title Case
	 */
	public static String getStrTitle(Object obj) {
		if (!isObjNull(obj)) {
			String modStr = obj.toString().trim().toLowerCase();
			String[] strArr = modStr.split(CmnConstants.SPACE);
			StringBuilder newStr = new StringBuilder(CmnConstants.EMPTY_STRING);
			for (int i = 0; i < strArr.length; i++) {
				strArr[i] = Character.toUpperCase(strArr[i].charAt(0)) + strArr[i].substring(1);
				newStr.append(strArr[i]).append(CmnConstants.SPACE);
			}
			return newStr.toString().trim();
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	public static Integer getInt(Object obj) {
		if (obj != null) {
			try {
				return Integer.parseInt(obj.toString().trim());
			} catch (Exception e) {
				return CmnConstants.ZERO;
			}
		} else {
			return CmnConstants.ZERO;
		}
	}


	/**
	 * This method will return a double value.
	 *
	 * @param obj
	 *             the number to obtain double value
	 * @return
	 */
	public static Double getDouble(Object obj) {
		return getDouble(obj, null);
	}


	/**
	 * This method will return a double value based on decimal format pattern.
	 *
	 * @param obj
	 *             the number to obtain double value
	 * @param pattern
	 *             decimal format pattern. eg. ##.##
	 * @return
	 */
	public static Double getDouble(Object obj, String pattern) {
		if (obj != null) {
			try {
				double d = Double.parseDouble(obj.toString().trim());

				if (!isObjNull(pattern)) {
					DecimalFormat df = new DecimalFormat(pattern);
					return Double.parseDouble(df.format(d));
				} else {
					return d;
				}
			} catch (Exception e) {
				return (double) 0;
			}
		} else {
			return (double) 0;
		}
	}


	/**
	 * Convert Object value to BigDecimal value. Object can be any String,
	 * Double, Integer, Long, etc
	 *
	 * @param input
	 *             Object which need to be convert to BigDecimal
	 * @return A BigDecimal value for specified Object
	 */
	public static BigDecimal getBigDecimal(Object input) {
		try {
			if (input instanceof String) {
				return new BigDecimal((String) input);
			} else if (input instanceof Double) {
				return BigDecimal.valueOf((Double) input);
			} else if (input instanceof Integer) {
				return new BigDecimal((Integer) input);
			} else if (input instanceof Long) {
				return new BigDecimal((Long) input);
			} else if (input instanceof BigInteger) {
				return new BigDecimal((BigInteger) input);
			} else if (input instanceof Float) {
				return BigDecimal.valueOf((Float) input);
			} else if (input instanceof Short) {
				return new BigDecimal((Short) input);
			} else if (input instanceof BigDecimal) {
				return (BigDecimal) input;
			} else {
				return new BigDecimal(CmnConstants.ZERO);
			}
		} catch (Exception e) {
			return new BigDecimal(CmnConstants.ZERO);
		}
	}


	public static Integer getListSize(List<?> o) {
		if (o != null && !o.isEmpty()) {
			return o.size();
		} else {
			return CmnConstants.ZERO;
		}
	}


	public static Boolean isListNull(List<?> o) {
		return (o != null && getListSize(o) > 0) ? Boolean.FALSE : Boolean.TRUE;
	}


	public static Boolean isListZero(List<?> o) {
		return (!isListNull(o) && o.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
	}


	public static boolean isEquals(String oriSrc, String compareSrc) {
		return (oriSrc != null && getStr(oriSrc).equals(getStr(compareSrc)));
	}


	public static boolean isEqualsCaseIgnore(String oriSrc, String compareSrc) {
		return (oriSrc != null && getStr(oriSrc).equalsIgnoreCase(getStr(compareSrc)));
	}


	public static boolean isEqualsAny(String oriSrc, String compareSrc) {
		boolean isEqual = false;
		String[] compareSrcArr = compareSrc.split(",");
		for (String element : compareSrcArr) {
			if (oriSrc != null && getStr(oriSrc).equals(getStr(element))) {
				isEqual = true;
				break;
			}
		}

		return isEqual;
	}


	public static boolean isEqualsCaseIgnoreAny(String oriSrc, String compareSrc) {
		boolean isEqual = false;
		String[] compareSrcArr = compareSrc.split(",");
		for (String element : compareSrcArr) {
			if (oriSrc != null && getStr(oriSrc).equalsIgnoreCase(getStr(element))) {
				isEqual = true;
				break;
			}
		}

		return isEqual;
	}


	public static boolean isObjNull(Object obj) {
		return !(obj != null && getStr(obj).length() > 0);
	}


	public static Boolean isMaxNoReached(Integer supplyNo, Integer compareNo) {
		return (supplyNo > compareNo);
	}


	/**
	 * Copies a range of characters into a new String.
	 *
	 * @param input
	 *             String need to be sub string
	 * @param start
	 *             the offset of the first character
	 * @param end
	 *             the offset one past the last character
	 * @return a new String containing the characters from start to end - 1
	 */
	public static String subString(String input, int start, int end) {
		if (input != null) {
			return getStr(input).substring(start, end);
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	/**
	 * Copies a range of characters into a new String.
	 *
	 * @param input
	 *             String need to be sub string
	 * @param start
	 *             the offset of the first character
	 * @return a new String containing the characters from start to the end of
	 *         the string
	 */
	public static String subString(String input, int start) {
		if (input != null) {
			return getStr(input).substring(start);
		} else {
			return CmnConstants.EMPTY_STRING;
		}
	}


	/**
	 * Check if the email address is valid (Required JavaMail class)
	 *
	 * @param email
	 *             The email address to be checked
	 * @return True is valid email address, False otherwise
	 * @author mhazwanh
	 */
	public static boolean isValidEmailAddress(String email) {
		boolean result = false;
		if (!isObjNull(email)) {
			result = email.matches("/[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}/");
		}
		return result;
	}


	public static java.sql.Timestamp getCurrentDateSQL() {

		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();

		return new java.sql.Timestamp(now.getTime());
	}


	public static java.sql.Timestamp convertDateToSQLDate(Date date) {

		return new java.sql.Timestamp(date.getTime());
	}


	public static java.sql.Timestamp getRecordModfiedDate() {
		return getRecordCreateDate();
	}


	public static java.sql.Timestamp getRecordCreateDate() {
		return getCurrentDateSQL();
	}


	private static final Logger LOGGER = LoggerFactory.getLogger(CmnUtil.class);


	public static Date convertDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			if (date != null) {
				return sdf.parse(date);
			} else {
				return null;
			}
		} catch (ParseException e) {
			LOGGER.error("convertDate Error: {}", e);
			return null;
		}
	}


	public static String convertDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return "";
		}
	}


	public static String convertQuotaSPPA(String appNo) {
		if (appNo == null) {
			return null;
		}

		if (appNo.contains(".")) {
			appNo = appNo.replace(".", "*");
			appNo = appNo.replace("-", "^");
			appNo = appNo.replace("/", "-");
			appNo = appNo.replace(" ", "_");
		} else {
			if (!appNo.contains("*")) {
				appNo = appNo.replace("/", "-");
			}
		}
		return appNo.toUpperCase();
	}


	public static String revertQuotaSPPA(String appNo) {
		if (appNo == null) {
			return null;
		}

		if (appNo.contains("*")) {
			appNo = appNo.replace("*", ".");
			appNo = appNo.replace("-", "/");
			appNo = appNo.replace("^", "-");
			appNo = appNo.replace("_", " ");
		} else {
			if (!appNo.contains(".")) {
				appNo = appNo.replace("-", "/");
			}
		}
		return appNo.toUpperCase();
	}


	public static List<String> getList(String values) {
		List<String> strList = new ArrayList<>();
		if (!CmnUtil.isObjNull(values)) {
			strList = Arrays.asList(values.split("\\s*,\\s*"));
			if (!CmnUtil.isListNull(strList)) {
				strList.removeAll(Collections.singleton(null));
				strList.removeAll(Collections.singleton(""));
			}
		}
		return strList;
	}


	public static boolean intToBoolean(int value) {
		return BooleanUtils.toBoolean(value);
	}

}
