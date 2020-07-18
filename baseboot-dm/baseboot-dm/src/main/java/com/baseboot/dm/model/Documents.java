/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.baseboot.dm.core.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Document(collection = "#{T(com.baseboot.dm.util.TenantGenerator).tenant()}")
public class Documents extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 106316930304813799L;
	
	@Id
	private String id;
	
	private String refno;
	
	private String docid;
	
	private String txnno;
	
	private String files_id;
	
	private String filename;
	
	private long length;
	
	private String contentType;
	
	private String version;
	
	private Date uploadDate;
	
	@Transient
	private byte[] content;

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

	public String getFiles_id() {
		return files_id;
	}

	public void setFiles_id(String files_id) {
		this.files_id = files_id;
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

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}	
	
}