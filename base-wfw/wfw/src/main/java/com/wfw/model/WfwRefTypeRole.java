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
@Table(name = "WFW_REF_TYPE_ROLE")
public class WfwRefTypeRole extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4434138416628551812L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_ROLE_ID")
	private Integer roleId;

	@Column(name = "REF_TYPE_ID")
	private Integer typeId;

	@Column(name = "ROLE_CD")
	private Integer roleCd;

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
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId
	 *             the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	 * @return the roleCd
	 */
	public Integer getRoleCd() {
		return roleCd;
	}


	/**
	 * @param roleCd
	 *             the roleCd to set
	 */
	public void setRoleCd(Integer roleCd) {
		this.roleCd = roleCd;
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

}
