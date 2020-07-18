/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_ROLE_MENU")
@JsonInclude(Include.NON_NULL)
public class IdmRoleMenu implements java.io.Serializable {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "ROLE_MENU_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_CODE")
	private IdmRole idmRole;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MENU_CODE")
	private IdmMenu idmMenu;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public IdmRoleMenu() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public IdmRole getIdmRole() {
		return idmRole;
	}


	public void setIdmRole(IdmRole idmRole) {
		this.idmRole = idmRole;
	}


	public IdmMenu getIdmMenu() {
		return idmMenu;
	}


	public void setIdmMenu(IdmMenu idmMenu) {
		this.idmMenu = idmMenu;
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

}