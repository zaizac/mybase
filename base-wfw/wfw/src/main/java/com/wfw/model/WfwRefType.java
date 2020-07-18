/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_REF_TYPE")
public class WfwRefType extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -7689438602371394540L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "TRAN_ID")
	private String tranId;

	@Column(name = "MODULE_ID")
	private String moduleId;

	@Column(name = "REDIRECT_PATH")
	private String redirectPath;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "AUTO_CLAIM")
	private Integer autoClaim;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}


	/**
	 * @param typeId
	 *             the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	/**
	 * @return the tranId
	 */
	public String getTranId() {
		return tranId;
	}


	/**
	 * @param tranId
	 *             the tranId to set
	 */
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}


	/**
	 * @param moduleId
	 *             the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	/**
	 * @return the redirectPath
	 */
	public String getRedirectPath() {
		return redirectPath;
	}


	/**
	 * @param redirectPath
	 *             the redirectPath to set
	 */
	public void setRedirectPath(String redirectPath) {
		this.redirectPath = redirectPath;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description
	 *             the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the autoClaim
	 */
	public Integer getAutoClaim() {
		return autoClaim;
	}


	/**
	 * @param autoClaim
	 *             the autoClaim to set
	 */
	public void setAutoClaim(Integer autoClaim) {
		this.autoClaim = autoClaim;
	}


	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * @param status
	 *             the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
