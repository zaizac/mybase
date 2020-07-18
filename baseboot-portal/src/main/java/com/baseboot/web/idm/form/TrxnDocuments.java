/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.form;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since 04/03/2018
 */
public class TrxnDocuments implements Serializable {

	private static final long serialVersionUID = -6233327454297054142L;

	private String docRefNo;

	private Integer docId;

	private String docMgtId;

	private String docContentType;

	private String title;

	private String fileName;

	private CustomMultipartFile file;

	private String refNo;

	private boolean docToRemove;


	public TrxnDocuments() {
	}


	public TrxnDocuments(String docRefNo, Integer docId, String docMgtId, String docContentType) {
		this.docRefNo = docRefNo;
		this.docId = docId;
		this.docMgtId = docMgtId;
		this.docContentType = docContentType;
	}


	public TrxnDocuments(String docRefNo, Integer docId, String docMgtId, String docContentType, boolean docToRemove) {
		this.docRefNo = docRefNo;
		this.docId = docId;
		this.docMgtId = docMgtId;
		this.docContentType = docContentType;
		this.docToRemove = docToRemove;
	}


	public String getDocRefNo() {
		return docRefNo;
	}


	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}


	public Integer getDocId() {
		return docId;
	}


	public void setDocId(Integer docId) {
		this.docId = docId;
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


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public CustomMultipartFile getFile() {
		return file;
	}


	public void setFile(CustomMultipartFile file) {
		this.file = file;
	}


	public boolean isDocToRemove() {
		return docToRemove;
	}


	public void setDocToRemove(boolean docToRemove) {
		this.docToRemove = docToRemove;
	}

}