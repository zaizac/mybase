/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.sdk.util;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import com.baseboot.report.sdk.constants.BaseConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class BaseUtil {

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
			return BaseConstants.EMPTY_STRING;
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
			return BaseConstants.EMPTY_STRING;
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
			return BaseConstants.EMPTY_STRING;
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
			String[] strArr = modStr.split(BaseConstants.SPACE);
			String newStr = BaseConstants.EMPTY_STRING;
			for (int i = 0; i < strArr.length; i++) {
				if (!isObjNull(strArr[i])) {
					strArr[i] = strArr[i].trim();
					strArr[i] = Character.toUpperCase(strArr[i].charAt(0)) + strArr[i].substring(1);
					newStr += strArr[i] + BaseConstants.SPACE;
				}
			}
			return newStr.trim();
		} else {
			return BaseConstants.EMPTY_STRING;
		}
	}


	public static Integer getInt(Object obj) {
		if (obj != null) {
			try {
				return Integer.parseInt(obj.toString().trim());
			} catch (Exception e) {
				return BaseConstants.ZERO;
			}
		} else {
			return BaseConstants.ZERO;
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
			if (input instanceof String)
				return new BigDecimal((String) input);
			else if (input instanceof Double)
				return new BigDecimal((Double) input);
			else if (input instanceof Integer)
				return new BigDecimal((Integer) input);
			else if (input instanceof Long)
				return new BigDecimal((Long) input);
			else if (input instanceof BigInteger)
				return new BigDecimal((BigInteger) input);
			else if (input instanceof Float)
				return new BigDecimal((Float) input);
			else if (input instanceof Short)
				return new BigDecimal((Short) input);
			else if (input instanceof BigDecimal)
				return (BigDecimal) input;
			else
				return new BigDecimal(BaseConstants.ZERO);
		} catch (Exception e) {
			return new BigDecimal(BaseConstants.ZERO);
		}
	}


	public static Integer getListSize(List o) {
		if (o != null && o.size() > 0) {
			return o.size();
		} else {
			return BaseConstants.ZERO;
		}
	}


	public static Boolean isListNull(List o) {
		if (o != null && getListSize(o) > 0) {
			return false;
		} else {
			return true;
		}
	}


	public static Boolean isListZero(List o) {
		if (!isListNull(o) && o.size() == 0) {
			return true;
		} else {
			return false;
		}
	}


	public static Boolean isListNullZero(List o) {
		if (isListNull(o) || (!isListNull(o) && o.size() == 0)) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isEquals(String oriSrc, String compareSrc) {
		if (oriSrc != null && getStr(oriSrc).equals(getStr(compareSrc))) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isEqualsCaseIgnore(String oriSrc, String compareSrc) {
		if (oriSrc != null && getStr(oriSrc).equalsIgnoreCase(getStr(compareSrc))) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isEqualsAny(String oriSrc, String compareSrc) {
		boolean isEqual = false;
		String compareSrcArr[] = compareSrc.split(",");
		for (int i = 0; i < compareSrcArr.length; i++) {
			if (oriSrc != null && getStr(oriSrc).equals(getStr(compareSrcArr[i]))) {
				isEqual = true;
				break;
			}
		}

		return isEqual;
	}


	public static boolean isEqualsCaseIgnoreAny(String oriSrc, String compareSrc) {
		boolean isEqual = false;
		String compareSrcArr[] = compareSrc.split(",");
		for (int i = 0; i < compareSrcArr.length; i++) {
			if (oriSrc != null && getStr(oriSrc).equalsIgnoreCase(getStr(compareSrcArr[i]))) {
				isEqual = true;
				break;
			}
		}

		return isEqual;
	}


	public static boolean isObjNull(Object obj) {
		if (obj != null && getStr(obj).length() > 0) {
			return false;
		} else {
			return true;
		}
	}


	public static Boolean isMaxNoReached(Integer supplyNo, Integer compareNo) {
		return (supplyNo > compareNo) ? true : false;
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
			return BaseConstants.EMPTY_STRING;
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
			return BaseConstants.EMPTY_STRING;
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
		if (!isObjNull(email))
			result = email.matches("/[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}/");
		return result;
	}


	/**
	 * Check if has number, change that number to bigDecimal presentation
	 * 
	 * @param string
	 * @return string with number presented as bigDecimal (has ',' symbol in
	 *         number)
	 * @author nazmiKadim
	 */
	public static String changeNumberFormatInString(String str) {
		DecimalFormat df = new DecimalFormat("#,###.00");
		StringBuffer sb = new StringBuffer();
		String[] split = str.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			Object dummy = split[i].replaceAll("\\D+", "");
			if (!isObjNull(dummy)) {
				String number = df.format(getBigDecimal(split[i]));
				sb.append(number + " ");
			} else {
				sb.append(split[i] + " ");
			}
		}
		return sb.toString();
	}

}
