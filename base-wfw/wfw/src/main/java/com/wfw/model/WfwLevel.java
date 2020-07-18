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
 * WfwLevel entity. 
 * 
 * @author Mary Jane Buenaventura
 * @since 08/06/2018
 */
@Entity
@Table(name = "WFW_LEVEL")
public class WfwLevel extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;
	
	@Id
	@Column(name = "LEVEL_ID")
	private String levelId;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "FLOW_NO")
	private Integer flowNo;
		
	@Column(name = "TYPE_ID")
	private String typeId;
	
	@Column(name = "CREATE_ID")
	private String createId;
	
	@Column(name = "CREATE_DT")
	private Timestamp createDt;
	
	@Column(name = "UPDATE_ID")
	private String updateId;
	
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;
	
	@Transient
	private List<WfwStatus> wfwStatusList;
	
	@Transient
	private String roles;

	public WfwLevel() {
	}

	
	public String getLevelId() {
		return levelId;
	}


	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = CmnUtil.getStrUpper(description);
	}


	public Integer getFlowNo() {
		return flowNo;
	}


	public void setFlowNo(Integer flowNo) {
		this.flowNo = flowNo;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
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

	public List<WfwStatus> getWfwStatusList() {
		return wfwStatusList;
	}


	public void setWfwStatusList(List<WfwStatus> wfwStatusList) {
		this.wfwStatusList = wfwStatusList;
	}


	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}

	
	
}