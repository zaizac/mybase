package com.wfw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WfwAsgnRolePk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "LEVEL_ID")
	private String levelId;
	
	@Column(name = "ROLE_ID")
	private String roleId;
	
	public WfwAsgnRolePk() {
	}
	
	public WfwAsgnRolePk(String levelId, String roleId) {
		this.levelId = levelId;
		this.roleId = roleId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public boolean equals(Object other) {
	    if (this == other)
	        return true;
	    if (!(other instanceof WfwAsgnRolePk))
	        return false;
	    WfwAsgnRolePk castOther = (WfwAsgnRolePk) other;
	    return this.levelId.equals(castOther.levelId) && this.roleId.equals(castOther.roleId);
	}

	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;
	    hash = hash * prime + this.levelId.hashCode();
	    hash = hash * prime + this.roleId.hashCode();
	    return hash;
	}
	
}
