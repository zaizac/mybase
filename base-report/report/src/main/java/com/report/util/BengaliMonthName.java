package com.report.util;


public enum BengaliMonthName {

	JAN("January", "Rvbyqvix"), FEB("February", "†deª“qvwi"), MARCH("March", "gvP©"), APRIL("April", "GwcÖj"), MAY(
			"May", "†g"), JUNE("June", "Ryb"), JULY("July", "RyjvB"), AUG("August", "AvM÷"), SEP("September",
			"†m‡Þ¤^i"), OCT("October", "A‡±vei"), NOV("November", "b‡f¤^i"), DEC("December", "wW‡m¤^i");

	private String name;

	private String pdfCode;


	BengaliMonthName(String name, String pdfCode) {

		this.pdfCode = pdfCode;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPdfCode() {
		return pdfCode;
	}


	public void setPdfCode(String pdfCode) {
		this.pdfCode = pdfCode;
	}


	public static String getCodeByName(String name) {
		for (BengaliMonthName month : BengaliMonthName.values()) {
			if (month.getName().equals(name)) {
				return month.getPdfCode();
			}
		}
		return "";
	}
}
