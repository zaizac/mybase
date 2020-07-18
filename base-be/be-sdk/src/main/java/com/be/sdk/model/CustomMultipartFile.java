/**
 * Copyright 2019
 */
package com.be.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class CustomMultipartFile implements Serializable {

	private static final long serialVersionUID = 6504304054277314612L;

	private String filename;

	private long size;

	private String contentType;

	private byte[] content;

	private int docId;

	private String docMgtId;

	private boolean compulsary;

	private String refNo;


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

}