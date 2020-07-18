/**
 * Copyright 2019
 */
package com.util.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class FileUpload implements Serializable {

	private static final long serialVersionUID = -4870108113915796925L;

	private Integer docId;

	private String docRefNo;

	private String docMgtId;

	private String docContentType;

	private boolean compulsary;

	private CustomMultipartFile file;

	private byte[] fileData;

	private String fileName;

	private String title;

	private Double acctBal;

	private String month;

	private String type;

	RefDocuments document;

	private String docTitle;


	public FileUpload() {
		// default implementation ignored
	}


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


	public byte[] getFileData() {
		return fileData;
	}


	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
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


	public CustomMultipartFile getFile() {
		return file;
	}


	public void setFile(CustomMultipartFile file) {
		this.file = file;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public RefDocuments getDocument() {
		return document;
	}


	public void setDocument(RefDocuments document) {
		this.document = document;
	}


	public String getDocTitle() {
		return docTitle;
	}


	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

}
