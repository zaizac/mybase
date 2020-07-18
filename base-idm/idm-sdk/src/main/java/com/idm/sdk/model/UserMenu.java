package com.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.util.constants.BaseConstants;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class UserMenu implements Serializable {

	private static final long serialVersionUID = 435740968912761385L;

	private String menuCode;

	private String menuParentCode;

	private int menuLevel;

	private int menuSequence;

	private String menuUrlCd;

	private String menuIcon;

	private List<UserMenu> menuChild;

	private LangDesc menuDesc;

	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp createDtTo;

	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtFrom;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDtTo;
	
	private String idmRoleMenus;


	/**
	 * @return the menuCode
	 */
	public String getMenuCode() {
		return menuCode;
	}


	/**
	 * @param menuCode
	 *             the menuCode to set
	 */
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}


	/**
	 * @return the menuParentCode
	 */
	public String getMenuParentCode() {
		return menuParentCode;
	}


	/**
	 * @param menuParentCode
	 *             the menuParentCode to set
	 */
	public void setMenuParentCode(String menuParentCode) {
		this.menuParentCode = menuParentCode;
	}


	/**
	 * @return the menuLevel
	 */
	public int getMenuLevel() {
		return menuLevel;
	}


	/**
	 * @param menuLevel 
	 *             the menuLevel to set
	 */
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}


	/**
	 * @return the menuSequence
	 */
	public int getMenuSequence() {
		return menuSequence;
	}


	/**
	 * @param menuSequence
	 *             the menuSequence to set
	 */
	public void setMenuSequence(int menuSequence) {
		this.menuSequence = menuSequence;
	}


	/**
	 * @return the menuUrlCd
	 */
	public String getMenuUrlCd() {
		return menuUrlCd;
	}


	/**
	 * @param menuUrlCd
	 *             the menuUrlCd to set
	 */
	public void setMenuUrlCd(String menuUrlCd) {
		this.menuUrlCd = menuUrlCd;
	}


	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}


	/**
	 * @param menuIcon
	 *             the menuIcon to set
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}


	/**
	 * @return the menuChild
	 */
	public List<UserMenu> getMenuChild() {
		return menuChild;
	}


	/**
	 * @param menuChild
	 *             the menuChild to set
	 */
	public void setMenuChild(List<UserMenu> menuChild) {
		this.menuChild = menuChild;
	}


	/**
	 * @return the menuDesc
	 */
	public LangDesc getMenuDesc() {
		return menuDesc;
	}


	/**
	 * @param menuDesc
	 *             the menuDesc to set
	 */
	public void setMenuDesc(LangDesc menuDesc) {
		this.menuDesc = menuDesc;
	}


	/**
	 * @return the createId
	 */
	public String getCreateId() {
		return createId;
	}


	/**
	 * @param createId
	 *             the createId to set
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	/**
	 * @return the createDt
	 */
	public Timestamp getCreateDt() {
		return createDt;
	}


	/**
	 * @param createDt
	 *             the createDt to set
	 */
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	/**
	 * @return the createDtFrom
	 */
	public Timestamp getCreateDtFrom() {
		return createDtFrom;
	}


	/**
	 * @param createDtFrom
	 *             the createDtFrom to set
	 */
	public void setCreateDtFrom(Timestamp createDtFrom) {
		this.createDtFrom = createDtFrom;
	}


	/**
	 * @return the createDtTo
	 */
	public Timestamp getCreateDtTo() {
		return createDtTo;
	}


	/**
	 * @param createDtTo
	 *             the createDtTo to set
	 */
	public void setCreateDtTo(Timestamp createDtTo) {
		this.createDtTo = createDtTo;
	}


	/**
	 * @return the updateId
	 */
	public String getUpdateId() {
		return updateId;
	}


	/**
	 * @param updateId
	 *             the updateId to set
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	/**
	 * @return the updateDt
	 */
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * @param updateDt
	 *             the updateDt to set
	 */
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	/**
	 * @return the updateDtFrom
	 */
	public Timestamp getUpdateDtFrom() {
		return updateDtFrom;
	}


	/**
	 * @param updateDtFrom
	 *             the updateDtFrom to set
	 */
	public void setUpdateDtFrom(Timestamp updateDtFrom) {
		this.updateDtFrom = updateDtFrom;
	}


	/**
	 * @return the updateDtTo
	 */
	public Timestamp getUpdateDtTo() {
		return updateDtTo;
	}


	/**
	 * @param updateDtTo 
	 *             the updateDtTo to set 
	 */
	public void setUpdateDtTo(Timestamp updateDtTo) {
		this.updateDtTo = updateDtTo;
	}


	public String getIdmRoleMenus() {
		return idmRoleMenus;
	}


	public void setIdmRoleMenus(String idmRoleMenus) {
		this.idmRoleMenus = idmRoleMenus;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}