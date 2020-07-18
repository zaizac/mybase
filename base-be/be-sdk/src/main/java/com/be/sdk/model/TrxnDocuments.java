/**
 * Copyright 2019
 */
package com.be.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since 04/03/2018
 */
public class TrxnDocuments implements Serializable, IQfCriteria<TrxnDocuments> {

	private static final long serialVersionUID = -6233327454297054142L;

	private String docRefNo;

	private Integer docId;

	private String docMgtId;

	private String appRefNo;

	private String appType;

	private String docContentType;

	private Boolean verify;

	private String remarks;

	private String title;

	private byte[] content;

	private String fileName;

	private String quotaRefNo;

	private CustomMultipartFile file;

	private String refNo;

	@JsonInclude(Include.NON_DEFAULT)
	private boolean docToRemove;

	@JsonInclude(Include.NON_DEFAULT)
	private boolean docIsChange;

	private Integer raProfId;


	public TrxnDocuments() {
		// DO NOTHING
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


	/**
	 * @return the appRefNo
	 */
	public String getAppRefNo() {
		return appRefNo;
	}


	/**
	 * @param appRefNo
	 *             the appRefNo to set
	 */
	public void setAppRefNo(String appRefNo) {
		this.appRefNo = appRefNo;
	}


	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}


	/**
	 * @param appType
	 *             the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}


	public String getDocContentType() {
		return docContentType;
	}


	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}


	/**
	 * @return the verify
	 */
	public Boolean getVerify() {
		return verify;
	}


	/**
	 * @param verify
	 *             the verify to set
	 */
	public void setVerify(Boolean verify) {
		this.verify = verify;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks
	 *             the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}


	/**
	 * @param content
	 *             the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
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


	/**
	 * @return the docIsChange
	 */
	public boolean isDocIsChange() {
		return docIsChange;
	}


	/**
	 * @param docIsChange
	 *             the docIsChange to set
	 */
	public void setDocIsChange(boolean docIsChange) {
		this.docIsChange = docIsChange;
	}


	public String getQuotaRefNo() {
		return quotaRefNo;
	}


	public void setQuotaRefNo(String quotaRefNo) {
		this.quotaRefNo = quotaRefNo;
	}


	public Integer getRaProfId() {
		return raProfId;
	}


	public void setRaProfId(Integer raProfId) {
		this.raProfId = raProfId;
	}

}