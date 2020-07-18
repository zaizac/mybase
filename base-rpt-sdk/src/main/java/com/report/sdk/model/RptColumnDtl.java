/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.report.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Naem Othman
 * @since Nov 14, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RptColumnDtl implements Serializable {

	private static final long serialVersionUID = 7234921356214608642L;

	private String column1;

	private String column2;

	private String column3;

	private String column4;

	private String column5;


	public RptColumnDtl() {
	}


	public RptColumnDtl(String column1) {
		this.column1 = column1;
	}


	public RptColumnDtl(String column1, String column2) {
		this.column1 = column1;
		this.column2 = column2;
	}


	public RptColumnDtl(String column1, String column2, String column3) {
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
	}


	public RptColumnDtl(String column1, String column2, String column3, String column4) {
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
	}


	public RptColumnDtl(String column1, String column2, String column3, String column4, String column5) {
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
		this.column5 = column5;
	}


	public String getColumn1() {
		return column1;
	}


	public void setColumn1(String column1) {
		this.column1 = column1;
	}


	public String getColumn2() {
		return column2;
	}


	public void setColumn2(String column2) {
		this.column2 = column2;
	}


	public String getColumn3() {
		return column3;
	}


	public void setColumn3(String column3) {
		this.column3 = column3;
	}


	public String getColumn4() {
		return column4;
	}


	public void setColumn4(String column4) {
		this.column4 = column4;
	}


	public String getColumn5() {
		return column5;
	}


	public void setColumn5(String column5) {
		this.column5 = column5;
	}

}