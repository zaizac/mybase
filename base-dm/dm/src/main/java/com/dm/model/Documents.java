/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The Class Documents.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Document(collection = "#{T(com.dm.util.TenantGenerator).TENANT}")
public class Documents implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 106316930304813799L;

	/** The id. */
	@Id
	private String id;

	/** The refno. */
	private String refno;

	/** The docid. */
	private String docid;

	/** The txnno. */
	private String txnno;

	/** The files id. */
	private String filesId;

	/** The filename. */
	private String filename;

	/** The length. */
	private long length;

	/** The content type. */
	private String contentType;

	/** The version. */
	private int version;

	/** The upload date. */
	private Date uploadDate;

	/** The content. */
	@Transient
	private byte[] content;

	private List<Documents> history;

	private String dmBucket;


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id
	 *             the new id
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Gets the refno.
	 *
	 * @return the refno
	 */
	public String getRefno() {
		return refno;
	}


	/**
	 * Sets the refno.
	 *
	 * @param refno
	 *             the new refno
	 */
	public void setRefno(String refno) {
		this.refno = refno;
	}


	/**
	 * Gets the docid.
	 *
	 * @return the docid
	 */
	public String getDocid() {
		return docid;
	}


	/**
	 * Sets the docid.
	 *
	 * @param docid
	 *             the new docid
	 */
	public void setDocid(String docid) {
		this.docid = docid;
	}


	/**
	 * Gets the txnno.
	 *
	 * @return the txnno
	 */
	public String getTxnno() {
		return txnno;
	}


	/**
	 * Sets the txnno.
	 *
	 * @param txnno
	 *             the new txnno
	 */
	public void setTxnno(String txnno) {
		this.txnno = txnno;
	}


	/**
	 * Gets the files id.
	 *
	 * @return the files id
	 */
	public String getFilesId() {
		return filesId;
	}


	/**
	 * Sets the files id.
	 *
	 * @param filesId
	 *             the new files id
	 */
	public void setFilesId(String filesId) {
		this.filesId = filesId;
	}


	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * Sets the filename.
	 *
	 * @param filename
	 *             the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}


	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public long getLength() {
		return length;
	}


	/**
	 * Sets the length.
	 *
	 * @param length
	 *             the new length
	 */
	public void setLength(long length) {
		this.length = length;
	}


	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}


	/**
	 * Sets the content type.
	 *
	 * @param contentType
	 *             the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}


	/**
	 * Sets the version.
	 *
	 * @param version
	 *             the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}


	/**
	 * Gets the upload date.
	 *
	 * @return the upload date
	 */
	public Date getUploadDate() {
		return uploadDate;
	}


	/**
	 * Sets the upload date.
	 *
	 * @param uploadDate
	 *             the new upload date
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}


	/**
	 * Sets the content.
	 *
	 * @param content
	 *             the new content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}


	public List<Documents> getHistory() {
		return history;
	}


	public void setHistory(List<Documents> history) {
		this.history = history;
	}


	public String getDmBucket() {
		return dmBucket;
	}


	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}

}