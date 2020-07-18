package com.wfw.sdk.model;


import java.io.Serializable;


@Deprecated
public class QuotaAppReport implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2290004839985452122L;

	private String sector;

	private String sectorAgency;

	private int noOfApprovedQuota;

	private int noOfRejectQuota;

	private int noOfKivQuota;

	private long total;


	public QuotaAppReport() {
	}


	public QuotaAppReport(String sector, String sectorAgency, long total) {
		this.sector = sector;
		this.sectorAgency = sectorAgency;
		this.total = total;
	}


	public QuotaAppReport(String sector, String sectorAgency, int noOfApprovedQuota, int noOfRejectQuota,
			int noOfKivQuota, long total) {
		this.sector = sector;
		this.sectorAgency = sectorAgency;
		this.noOfApprovedQuota = noOfApprovedQuota;
		this.noOfRejectQuota = noOfRejectQuota;
		this.noOfKivQuota = noOfKivQuota;
		this.total = total;
	}


	public String getSector() {
		return sector;
	}


	public void setSector(String sector) {
		this.sector = sector;
	}


	public String getSectorAgency() {
		return sectorAgency;
	}


	public void setSectorAgency(String sectorAgency) {
		this.sectorAgency = sectorAgency;
	}


	public int getNoOfApprovedQuota() {
		return noOfApprovedQuota;
	}


	public void setNoOfApprovedQuota(int noOfApprovedQuota) {
		this.noOfApprovedQuota = noOfApprovedQuota;
	}


	public int getNoOfRejectQuota() {
		return noOfRejectQuota;
	}


	public void setNoOfRejectQuota(int noOfRejectQuota) {
		this.noOfRejectQuota = noOfRejectQuota;
	}


	public int getNoOfKivQuota() {
		return noOfKivQuota;
	}


	public void setNoOfKivQuota(int noOfKivQuota) {
		this.noOfKivQuota = noOfKivQuota;
	}


	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
	}

}
