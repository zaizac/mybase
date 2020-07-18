/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class MenuDto implements Serializable {

	private static final long serialVersionUID = -1243145997220661360L;

	private String menuCode;

	private String menuParentCode;

	private String menuDesc;

	private String menuDescMy;

	private String crtUsrId;

	private Timestamp crtTs;

	private String modUsrId;

	private Timestamp modTs;

	private int menuLevel;

	private String menuUrlCd;

	private LangDesc langDesc;


	public String getMenuUrlCd() {
		return menuUrlCd;
	}


	public void setMenuUrlCd(String menuUrlCd) {
		this.menuUrlCd = menuUrlCd;
	}


	public String getMenuDescMy() {
		return menuDescMy;
	}


	public void setMenuDescMy(String menuDescMy) {
		this.menuDescMy = menuDescMy;
	}


	public int getMenuLevel() {
		return menuLevel;
	}


	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}


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


	public String getMenuDesc() {
		return menuDesc;
	}


	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}


	public String getCrtUsrId() {
		return crtUsrId;
	}


	public void setCrtUsrId(String crtUsrId) {
		this.crtUsrId = crtUsrId;
	}


	public Timestamp getCrtTs() {
		return crtTs;
	}


	public void setCrtTs(Timestamp crtTs) {
		this.crtTs = crtTs;
	}


	public String getModUsrId() {
		return modUsrId;
	}


	public void setModUsrId(String modUsrId) {
		this.modUsrId = modUsrId;
	}


	public Timestamp getModTs() {
		return modTs;
	}


	public void setModTs(Timestamp modTs) {
		this.modTs = modTs;
	}


	public LangDesc getLangDesc() {
		return langDesc;
	}


	public void setLangDesc(LangDesc langDesc) {
		this.langDesc = langDesc;
	}

}
