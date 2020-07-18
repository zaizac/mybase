package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.be.core.AbstractEntity;
import com.be.sdk.model.CustomMultipartFile;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the BE_TRXN_DOCUMENTS database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Entity
@Table(name = "BE_TRXN_DOCUMENTS")
public class BeTrxnDocument extends AbstractEntity implements Serializable, IQfCriteria<BeTrxnDocument> {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BeTrxnDocumentPK id;

	@Column(name = "APP_REF_NO")
	private String appRefNo;

	@Column(name = "APP_TYPE")
	private String appType;

	@Column(name = "DOC_CONTENT_TYPE")
	private String docContentType;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "VERIFY")
	private Boolean verify;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Transient
	CustomMultipartFile file;


	public BeTrxnDocument() {
	}


	public BeTrxnDocument(BeTrxnDocumentPK id, String appRefNo, String docContentType) {
		this.id = id;
		this.appRefNo = appRefNo;
		this.docContentType = docContentType;
	}


	/**
	 * @return the id
	 */
	public BeTrxnDocumentPK getId() {
		return id;
	}


	/**
	 * @param id
	 *             the id to set
	 */
	public void setId(BeTrxnDocumentPK id) {
		this.id = id;
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


	/**
	 * @return the docContentType
	 */
	public String getDocContentType() {
		return docContentType;
	}


	/**
	 * @param docContentType
	 *             the docContentType to set
	 */
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


	/**
	 * @return the createDt
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the createId
	 */
	@Override
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the updateDt
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the updateId
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the file
	 */
	public CustomMultipartFile getFile() {
		return file;
	}


	/**
	 * @param file
	 *             the file to set
	 */
	public void setFile(CustomMultipartFile file) {
		this.file = file;
	}

}