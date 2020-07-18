package com.wfw.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * WfwAsgnTran entity. @author Kamruzzaman
 */
@Entity
@Table(name = "WFW_ASGN_TRAN")
public class WfwAsgnTran extends AbstractEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3364979286985840805L;

	@Id
	@Column(name = "TRAN_ID")
	private String tranId;

	@Column(name = "MODULE_ID")
	private String moduleId;

	@Column(name = "TYPE_ID")
	private String typeId;

	@Column(name = "VIEW_PATH")
	private String viewPath;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public WfwAsgnTran() {
	}


	public String getTranId() {
		return tranId;
	}


	public void setTranId(String tranId) {
		this.tranId = tranId;
	}


	public String getModuleId() {
		return moduleId;
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getViewPath() {
		return viewPath;
	}


	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
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