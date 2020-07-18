/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * @author michelle.angela
 *
 */
@Entity
@Table(name = "WFW_REF_LEVEL")
public class WfwRefLevel extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 5566771820710665080L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REF_LEVEL_ID")
	private Integer levelId;

	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FLOW_NO")
	private Integer flowNo;

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

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "refLevel", orphanRemoval = true)
	@JsonManagedReference
	private List<WfwRefLevelRole> refLevelRoleList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "refLevel")
	@JsonManagedReference
	private List<WfwRefTypeAction> refTypeActionList;


	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}


	/**
	 * @param levelId
	 *             the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}


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
	 * @return the flowNo
	 */
	public Integer getFlowNo() {
		return flowNo;
	}


	/**
	 * @param flowNo
	 *             the flowNo to set
	 */
	public void setFlowNo(Integer flowNo) {
		this.flowNo = flowNo;
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
	 * @return the refLevelRoleList
	 */
	public List<WfwRefLevelRole> getRefLevelRoleList() {
		return refLevelRoleList;
	}


	/**
	 * @param refLevelRoleList
	 *             the refLevelRoleList to set
	 */
	public void setRefLevelRoleList(List<WfwRefLevelRole> refLevelRoleList) {
		this.refLevelRoleList = refLevelRoleList;
	}


	/**
	 * @return the refTypeActionList
	 */
	public List<WfwRefTypeAction> getRefTypeActionList() {
		return refTypeActionList;
	}


	/**
	 * @param refTypeActionList
	 *             the refTypeActionList to set
	 */
	public void setRefTypeActionList(List<WfwRefTypeAction> refTypeActionList) {
		this.refTypeActionList = refTypeActionList;
	}

}
