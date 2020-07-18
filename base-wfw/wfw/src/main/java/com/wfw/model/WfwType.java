/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wfw.sdk.util.CmnUtil;


/**
 * WfwType entity.
 * 
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Entity
@Table(name = "WFW_TYPE")
public class WfwType extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "TYPE_ID")
	private String typeId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ASSIGN_TYPE")
	private String assignType;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Transient
	private List<WfwLevel> wfwLevelList;

	@Transient
	private String tranId;

	@Transient
	private String moduleId;

	@Transient
	private String viewPath;


	public WfwType() {
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = CmnUtil.getStrUpper(description);
	}


	public String getAssignType() {
		return assignType;
	}


	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getUpdateId() {
		return updateId;
	}


	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public List<WfwLevel> getWfwLevelList() {
		return wfwLevelList;
	}


	public void setWfwLevelList(List<WfwLevel> wfwLevelList) {
		this.wfwLevelList = wfwLevelList;
	}


	public String getTranId() {
		return CmnUtil.getStr(tranId);
	}


	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	public String getModuleId() {
		return CmnUtil.getStr(moduleId);
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public String getViewPath() {
		return CmnUtil.getStr(viewPath);
	}


	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

}