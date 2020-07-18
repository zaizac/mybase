package com.wfw.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * WfwAsgnRole entity. @author Kamruzzaman
 */
@Entity
@Table(name = "WFW_ASGN_ROLE")
public class WfwAsgnRole extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@EmbeddedId
	private WfwAsgnRolePk wfwAsgnRolePk;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwAsgnRole() {
	}


	public WfwAsgnRolePk getWfwAsgnRolePk() {
		return wfwAsgnRolePk;
	}


	public void setWfwAsgnRolePk(WfwAsgnRolePk wfwAsgnRolePk) {
		this.wfwAsgnRolePk = wfwAsgnRolePk;
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

}