/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.form;


import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class FileUpload implements Serializable {

	private static final long serialVersionUID = -4870108113915796925L;

	private Integer docId;

	private String docRefNo;

	private String docMgtId;

	private String docContentType;

	private boolean compulsary;

	private CustomMultipartFile file;

	private String fileName;

	private String title;

	@NumberFormat(style = Style.NUMBER, pattern = "#,###")
	private Double acctBal;

	private String month;

	private boolean remove;

	private String type;


	public Integer getDocId() {
		return docId;
	}


	public void setDocId(Integer docId) {
		this.docId = docId;
	}


	public String getDocRefNo() {
		return docRefNo;
	}


	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}


	public String getDocMgtId() {
		return docMgtId;
	}


	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}


	public String getDocContentType() {
		return docContentType;
	}


	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}


	public boolean isCompulsary() {
		return compulsary;
	}


	public void setCompulsary(boolean compulsary) {
		this.compulsary = compulsary;
	}


	public CustomMultipartFile getFile() {
		return file;
	}


	public void setFile(CustomMultipartFile file) {
		this.file = file;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Double getAcctBal() {
		return acctBal;
	}


	public void setAcctBal(Double acctBal) {
		this.acctBal = acctBal;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public void setRemove(boolean remove) {
		this.remove = remove;
	}


	public boolean isRemove() {
		return remove;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
