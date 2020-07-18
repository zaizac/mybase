/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.constants;

/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public enum MonthsEnum {

	JAN(1, "January"),
	FEB(2, "February"),
	MAR(3, "March"),
	APR(4, "April"),
	MAY(5, "May"),
	JUN(6, "June"),
	JUL(7, "July"),
	AUG(8, "August"),
	SEP(9, "September"),
	OCT(10, "October"),
	NOV(11, "November"),
	DEC(12, "December");

	private int key;

	private String value;


	private MonthsEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}


	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public static String getValue(int key) {
		for (MonthsEnum val : MonthsEnum.values()) {
			if (val.key == key) {
				return val.value;
			}
		}
		return null;
	}


	public static Integer getKey(String value) {
		for (MonthsEnum val : MonthsEnum.values()) {
			if (val.value.trim().toUpperCase().equals(value.trim().toUpperCase())) {
				return val.key;
			}
		}
		return null;
	}

}