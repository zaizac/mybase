/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.bff.cmn.form;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class CustomMultipartFile implements Serializable {

	private static final long serialVersionUID = -6058102772778256646L;

	private String filename;

	private long size;

	private String contentType;

	private byte[] content;

	private int docId;

	private String docMgtId;

	private boolean compulsary;

	private String refNo;

	private String dmBucket;


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public long getSize() {
		return size;
	}


	public void setSize(long size) {
		this.size = size;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}


	public int getDocId() {
		return docId;
	}


	public void setDocId(int docId) {
		this.docId = docId;
	}


	public String getDocMgtId() {
		return docMgtId;
	}


	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}


	public boolean isCompulsary() {
		return compulsary;
	}


	public void setCompulsary(boolean compulsary) {
		this.compulsary = compulsary;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public String getDmBucket() {
		return dmBucket;
	}


	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}

}