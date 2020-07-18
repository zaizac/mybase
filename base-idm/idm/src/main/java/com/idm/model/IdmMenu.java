package com.idm.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.idm.util.LangDescConverter;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Entity
@Table(name = "IDM_MENU")
@NamedQuery(name = "IdmMenu.findAll", query = "SELECT i FROM IdmMenu i")
public class IdmMenu extends AbstractEntity implements java.io.Serializable, IQfCriteria<IdmMenu> {

	private static final long serialVersionUID = -1533934058750880876L;

	@Id
	@Column(name = "MENU_CODE", unique = true, nullable = false, length = 20)
	private String menuCode;

	@Column(name = "MENU_PARENT_CODE")
	@NotNull
	private String menuParentCode;

	@Column(name = "MENU_DESC")
	@Convert(converter = LangDescConverter.class)
	private LangDesc menuDesc;

	@Column(name = "MENU_LEVEL")
	private Integer menuLevel;

	@Column(name = "MENU_SEQUENCE")
	private Integer menuSequence;

	@Column(name = "MENU_URL")
	@NotNull
	private String menuUrlCd;

	@Column(name = "MENU_ICON")
	private String menuIcon;

	@Column(name = "CREATE_ID")
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@JsonIgnoreProperties("idmMenu")
	@OneToMany(mappedBy = "idmMenu", fetch = FetchType.LAZY)
	private List<IdmRoleMenu> idmRoleMenus;

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


	public Integer getMenuLevel() {
		return menuLevel;
	}


	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}


	public Integer getMenuSequence() {
		return menuSequence;
	}


	public void setMenuSequence(Integer menuSequence) {
		this.menuSequence = menuSequence;
	}


	public String getMenuUrlCd() {
		return menuUrlCd;
	}


	public void setMenuUrlCd(String menuUrlCd) {
		this.menuUrlCd = menuUrlCd;
	}


	public String getMenuIcon() {
		return menuIcon;
	}


	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
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


	public List<IdmMenu> getMenuChild() {
		return menuChild;
	}


	public void setMenuChild(List<IdmMenu> menuChild) {
		this.menuChild = menuChild;
	}


	public List<IdmRoleMenu> getIdmRoleMenus() {
		return idmRoleMenus;
	}


	public void setIdmRoleMenus(List<IdmRoleMenu> idmRoleMenus) {
		this.idmRoleMenus = idmRoleMenus;
	}

}