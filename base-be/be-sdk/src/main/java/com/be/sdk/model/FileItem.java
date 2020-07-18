/**
 * Copyright 2019
 */
package com.be.sdk.model;

import java.io.Serializable;

/**
 * @author Mary Jane Buenaventura
 * @since Jan 26, 2015
 */
public class FileItem implements Serializable {

	private static final long serialVersionUID = -6058102772778256646L;

	private String refno;
	private String docid;
	private String txnno;
	private String filename;
	private long length;
	private String contentType;
	private byte[] content;

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getTxnno() {
		return txnno;
	}

	public void setTxnno(String txnno) {
		this.txnno = txnno;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
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

}