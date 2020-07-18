/**
 * Copyright 2019. 
 */
package com.idm.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
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

}
