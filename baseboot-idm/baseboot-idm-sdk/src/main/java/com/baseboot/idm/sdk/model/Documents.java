/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(Include.NON_NULL)
public class Documents implements Serializable {

	private static final long serialVersionUID = 106316930304813799L;

	private String id;

	private String refno;

	private String docid;

	private String txnno;

	private String fileId;

	private String filename;

	private long length;

	private String contentType;

	private String version;

	private Timestamp uploadDate;

	private byte[] content;

	private String contentString;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


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


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
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


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Timestamp getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}


	public String getContentString() {
		return contentString;
	}


	public void setContentString(String contentString) {
		this.contentString = contentString;
	}
}