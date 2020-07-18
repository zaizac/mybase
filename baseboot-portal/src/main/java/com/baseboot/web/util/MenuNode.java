package com.baseboot.web.util;

import java.util.List;

public class MenuNode {

	private String val;
    private String menuDesc;
    private String navUrl;
    private int level;
	private MenuNode parent;
	private List<MenuNode> children;
	private int sequence;
	
	public MenuNode(String val, String menuDesc, String navUrl, MenuNode parent, List<MenuNode> children, int sequence) {
		this.val = val;
		this.menuDesc = menuDesc;
		this.navUrl = navUrl;
		this.parent = parent;
		this.children = children;
		if(parent!=null)
			this.level = (int) parent.getLevel() + 1;
		else 
			this.level = 0;
		this.sequence = sequence;
	}
	
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public MenuNode getParent() {
		return parent;
	}
	public void setParent(MenuNode parent) {
		this.parent = parent;
	}
	public List<MenuNode> getChildren() {
		return children;
	}
	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getNavUrl() {
		return navUrl;
	}

	public void setNavUrl(String navUrl) {
		this.navUrl = navUrl;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}