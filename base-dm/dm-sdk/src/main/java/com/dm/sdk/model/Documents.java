/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class Documents.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(Include.NON_NULL)
public class Documents implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 106316930304813799L;

	/** The id. */
	private String id;

	/** The refno. */
	private String refno;

	/** The docid. */
	private Integer docid;

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
	private String version;

	/** The upload date. */
	private Timestamp uploadDate;

	/** The content. */
	private byte[] content;

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
	public Integer getDocid() {
		return docid;
	}


	/**
	 * Sets the docid.
	 *
	 * @param docid
	 *             the new docid
	 */
	public void setDocid(Integer docid) {
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
	public String getVersion() {
		return version;
	}


	/**
	 * Sets the version.
	 *
	 * @param version
	 *             the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * Gets the upload date.
	 *
	 * @return the upload date
	 */
	public Timestamp getUploadDate() {
		return uploadDate;
	}


	/**
	 * Sets the upload date.
	 *
	 * @param uploadDate
	 *             the new upload date
	 */
	public void setUploadDate(Timestamp uploadDate) {
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


	public String getDmBucket() {
		return dmBucket;
	}


	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}
}