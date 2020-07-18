/**
 * Copyright 2019
 */
package com.be.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the BE_CTRL_GEN database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Embeddable
public class BeCtrlGenPK implements Serializable {

	private static final long serialVersionUID = 914313103569468462L;

	@Column(name = "CTRL_GEN_DT", length = 8, nullable = false)
	private String ctrlGenDt;

	@Column(name = "TRXN_CD", length = 5, nullable = false)
	private String trxnCd;


	public BeCtrlGenPK() {
	}


	public BeCtrlGenPK(String ctrlGenDt, String trxnCd) {
		this.ctrlGenDt = ctrlGenDt;
		this.trxnCd = trxnCd;
	}


	public String getCtrlGenDt() {
		return ctrlGenDt;
	}


	public void setCtrlGenDt(String ctrlGenDt) {
		this.ctrlGenDt = ctrlGenDt;
	}


	public String getTrxnCd() {
		return trxnCd;
	}


	public void setTrxnCd(String trxnCd) {
		this.trxnCd = trxnCd;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BeCtrlGenPK)) {
			return false;
		}
		BeCtrlGenPK castOther = (BeCtrlGenPK) other;
		return ctrlGenDt.equals(castOther.ctrlGenDt) && (trxnCd == castOther.trxnCd);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ctrlGenDt.hashCode();
		hash = hash * prime + trxnCd.hashCode();
		return hash;
	}

}