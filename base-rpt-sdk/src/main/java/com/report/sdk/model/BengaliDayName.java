package com.report.sdk.model;


public enum BengaliDayName {

	ONE(1, "GK"),
	TWO(2, "`yB"),
	THREE(3, "wZb"),
	FOUR(4, "Pvi"),
	FIVE(5, "cvuP"),
	SIX(6, "Qq"),
	SEVEN(7, "mvZ"),
	EIGHT(8, "AvU"),
	NINE(9, "bq"),
	TEN(10, "`k");

	private String code;

	private Integer num;


	BengaliDayName(Integer num, String code) {
		this.code = code;
		this.num = num;
	}


	public static String getCodeByName(Integer num) {
		for (BengaliDayName day : BengaliDayName.values()) {
			if (day.getNum().equals(num)) {
				return day.getCode();
			}
		}
		return "";
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Integer getNum() {
		return num;
	}


	public void setNum(Integer num) {
		this.num = num;
	}
}
