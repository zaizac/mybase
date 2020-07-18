/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.bstsb.idm.core.AbstractEntity;
import com.bstsb.idm.sdk.model.LangDesc;
import com.bstsb.idm.util.LangDescConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_MENU")
@JsonInclude(Include.NON_NULL)
public class IdmMenu extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "MENU_CODE")
	private String menuCode;

	@Column(name = "MENU_PARENT_CODE")
	@NotNull
	private String menuParentCode;

	@Column(name = "MENU_DESC")
	@Convert(converter = LangDescConverter.class)
	private LangDesc menuDesc;

	@Column(name = "MENU_DESC_MY")
	private String menuDescMy;

	@Column(name = "MENU_LEVEL")
	private int menuLevel;

	@Column(name = "MENU_SEQUENCE")
	private int menuSequence;

	@Column(name = "MENU_URL")
	@NotNull
	private String menuUrlCd;

	@Column(name = "MENU_ICON")
	private String menuIcon;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Transient
	private List<IdmMenu> menuChild;


	public String getMenuCode() {
		return menuCode;
	}


	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}


	public String getMenuParentCode() {
		return menuParentCode;
	}


	public void setMenuParentCode(String menuParentCode) {
		this.menuParentCode = menuParentCode;
	}


	public LangDesc getMenuDesc() {
		return menuDesc;
	}


	public void setMenuDesc(LangDesc menuDesc) {
		this.menuDesc = menuDesc;
	}


	public String getMenuUrlCd() {
		return menuUrlCd;
	}


	public void setMenuUrlCd(String menuUrlCd) {
		this.menuUrlCd = menuUrlCd;
	}


	@Override
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public int getMenuLevel() {
		return menuLevel;
	}


	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}


	public int getMenuSequence() {
		return menuSequence;
	}


	public void setMenuSequence(int menuSequence) {
		this.menuSequence = menuSequence;
	}


	public String getMenuIcon() {
		return menuIcon;
	}


	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}


	public List<IdmMenu> getMenuChild() {
		return menuChild;
	}


	public void setMenuChild(List<IdmMenu> menuChild) {
		this.menuChild = menuChild;
	}


	public String getMenuDescMy() {
		return menuDescMy;
	}


	public void setMenuDescMy(String menuDescMy) {
		this.menuDescMy = menuDescMy;
	}

}