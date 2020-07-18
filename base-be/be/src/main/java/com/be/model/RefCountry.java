package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_COUNTRY database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 * @author hafidz.malik
 * @since Oct 29, 2019
 */
@Entity
@Table(name = "REF_COUNTRY")
public class RefCountry extends AbstractEntity implements Serializable, IQfCriteria<RefCountry> {

	private static final long serialVersionUID = -6510355585641387606L;

	@Id
	@Column(name = "CNTRY_CD")
	private String cntryCd;

	@Column(name = "CNTRY_DESC")
	private String cntryDesc;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "CNTRY_IND")
	private boolean cntryInd;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "INTL_CALL_CD")
	private String intlCallCd;

	@Column(name = "NTNLTY_DESC")
	private String ntnltyDesc;
	
	@Column(name="MC_PROF_TYPE_MTDT_ID")
	private Integer mcProfTypeMtdtId;
	
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;


	public RefCountry() {
		// DO NOTHING
	}


	public String getCntryCd() {
		return cntryCd;
	}


	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}


	public String getCntryDesc() {
		return cntryDesc;
	}


	public void setCntryDesc(String cntryDesc) {
		this.cntryDesc = cntryDesc;
	}


	public boolean getCntryInd() {
		return cntryInd;
	}


	public void setCntryInd(boolean cntryInd) {
		this.cntryInd = cntryInd;
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
	public String getCreateId() {
		return createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getIntlCallCd() {
		return intlCallCd;
	}


	public void setIntlCallCd(String intlCallCd) {
		this.intlCallCd = intlCallCd;
	}


	public String getNtnltyDesc() {
		return ntnltyDesc;
	}


	public void setNtnltyDesc(String ntnltyDesc) {
		this.ntnltyDesc = ntnltyDesc;
	}


	public Integer getMcProfTypeMtdtId() {
		return mcProfTypeMtdtId;
	}


	public void setMcProfTypeMtdtId(Integer mcProfTypeMtdtId) {
		this.mcProfTypeMtdtId = mcProfTypeMtdtId;
	}


	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	@Override
	public String getUpdateId() {
		return updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

}