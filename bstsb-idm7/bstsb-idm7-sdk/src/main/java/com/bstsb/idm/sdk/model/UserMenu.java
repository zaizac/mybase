/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;
import java.util.List;

import com.bstsb.util.BaseUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserMenu implements Serializable {

	private static final long serialVersionUID = 435740968912761385L;

	private String menuCode;

	private String menuParentCode;

	private LangDesc menuDesc;

	private int menuLevel;

	private int menuSequence;

	private String menuUrlCd;

	private String menuIcon;

	private List<UserMenu> menuChild;

	private LangDesc langDesc;


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


	public String getMenuIcon() {
		return menuIcon;
	}


	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
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


	public String getMenuUrlCd() {
		return menuUrlCd;
	}


	public void setMenuUrlCd(String menuUrlCd) {
		this.menuUrlCd = menuUrlCd;
	}


	public List<UserMenu> getMenuChild() {
		return menuChild;
	}


	public void setMenuChild(List<UserMenu> menuChild) {
		this.menuChild = menuChild;
	}


	public LangDesc getLangDesc() {
		return langDesc;
	}


	public void setLangDesc(LangDesc langDesc) {
		this.langDesc = langDesc;
	}

}