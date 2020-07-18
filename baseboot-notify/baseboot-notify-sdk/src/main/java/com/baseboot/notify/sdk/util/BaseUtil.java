/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.util;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import com.baseboot.notify.sdk.constants.BaseConstants;


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
				strArr[i] = Character.toUpperCase(strArr[i].charAt(0)) + strArr[i].substring(1);
				newStr += strArr[i] + BaseConstants.SPACE;
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
	 * Transfer all properties to Target object from Originating (Source)
	 * object.<br>
	 * Change the prefix naming for the target object field/method.
	 * 
	 * @param obj1
	 *             origin object where the properties being referred
	 * @param obj2
	 *             target object to retrieve the properties
	 * @param obj1Prefix
	 *             origin object prefix for field/method name
	 * @param obj2Prefix
	 *             target object prefix for field/method name
	 */
	public static Object transferProperties(Object obj1, Object obj2, String obj1Prefix, String obj2Prefix) {

		// System.out.println("---------------");
		// System.out.println("Class Name 1: " +
		// obj1.getClass().getCanonicalName());
		// System.out.println("Class Name 2: " +
		// obj2.getClass().getCanonicalName());
		// System.out.println("---------------");

		// Loop all Object 1 fields
		for (Field f : obj1.getClass().getDeclaredFields()) {

			String obj1FieldName = f.getName();
			String obj2FieldName = obj1FieldName.replace(obj1Prefix, obj2Prefix);
			String obj1GetMethodName = "get" + WordUtils.capitalize(obj1FieldName);
			String obj2SetMethodName = "set" + WordUtils.capitalize(obj2FieldName);
			Object obj1FieldData = null;

			try {
				// Fetch Object 1 Data
				try {
					obj1FieldData = obj1.getClass().getMethod(obj1GetMethodName).invoke(obj1);
				} catch (Exception e) {
					throw new Exception("Data not found for Object 1 Method [" + obj1GetMethodName + "]");
				}

				// Set Object 2 Data with Object 1 Data
				try {
					obj2.getClass().getMethod(obj2SetMethodName, f.getType()).invoke(obj2, obj1FieldData);
				} catch (Exception e) {
					throw new Exception("Object 2 Method not found [" + obj2SetMethodName + "]");
				}
			} catch (Exception e) {
				e.getMessage();
			}

		}
		return obj2;
	}


	/**
	 * Transfer all properties to Target object from Originating (Source)
	 * object.<br>
	 * Change the prefix naming for the target object field/method.
	 * 
	 * @param obj1
	 *             origin object where the properties being referred
	 * @param obj2
	 *             target object to retrieve the properties
	 * @param obj1Prefix
	 *             origin object prefix for field/method name
	 * @param obj2Prefix
	 *             target object prefix for field/method name
	 * @param transEmptyField
	 *             tranfer empty values
	 */
	public static Object transferProperties(Object obj1, Object obj2, String obj1Prefix, String obj2Prefix,
			boolean transEmptyField) {

		// System.out.println("---------------");
		// System.out.println("Class Name 1: " +
		// obj1.getClass().getCanonicalName());
		// System.out.println("Class Name 2: " +
		// obj2.getClass().getCanonicalName());
		// System.out.println("---------------");

		// Loop all Object 1 fields
		for (Field f : obj1.getClass().getDeclaredFields()) {

			String obj1FieldName = f.getName();
			String obj2FieldName = obj1FieldName.replace(obj1Prefix, obj2Prefix);
			String obj1GetMethodName = "get" + WordUtils.capitalize(obj1FieldName);
			String obj2SetMethodName = "set" + WordUtils.capitalize(obj2FieldName);
			Object obj1FieldData = null;
			Object obj2FieldData = null;

			try {
				// Fetch Object 1 Data
				try {
					obj1FieldData = obj1.getClass().getMethod(obj1GetMethodName).invoke(obj1);
				} catch (Exception e) {
					throw new Exception("Data not found for Object 1 Method [" + obj1GetMethodName + "]");
				}

				// Set Object 2 Data with Object 1 Data
				try {
					if (!transEmptyField) {
						if (!isObjNull(obj1FieldData)) {
							obj2.getClass().getMethod(obj2SetMethodName, f.getType()).invoke(obj2,
									obj1FieldData);
							obj2FieldData = obj2.getClass()
									.getMethod("get" + WordUtils.capitalize(obj2FieldName)).invoke(obj2);
						}
					} else {
						obj2.getClass().getMethod(obj2SetMethodName, f.getType()).invoke(obj2, obj1FieldData);
					}
				} catch (Exception e) {
					throw new Exception("Object 2 Method not found [" + obj2SetMethodName + "]");
				}
				// System.out.println("---------------");
				// System.out.println("Origin Data : obj1GetMethodName -" +
				// obj1GetMethodName + " obj1FieldData - " + obj1FieldData);
				// System.out.println("Target Data : obj2SetMethodName -" +
				// obj2SetMethodName + " obj2FieldData - " + obj2FieldData);
			} catch (Exception e) {
				e.getMessage();
			}

		}
		return obj2;
	}
}
