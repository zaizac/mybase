/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstsb.idm.core.AbstractEntity;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
@Entity
@Table(name = "IDM_FCM_DEVICE")
public class IdmFcmDevice extends AbstractEntity {

	@Id
	@Column(name = "DEVICE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "FCM_ID")
	private Integer fcmId;

	@Column(name = "ACCESS_TOKEN")
	private String accessToken;

	@Column(name = "MACHINE_ID")
	private String machineId;

	@Column(name = "SDK_VERSION")
	private String sdkVersion;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "BRAND")
	private String brand;

	@Column(name = "MANUFACTURER")
	private String manufaceturer;

	@Column(name = "GEO_LOCATION")
	private String geoLocation;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getFcmId() {
		return fcmId;
	}


	public void setFcmId(Integer fcmId) {
		this.fcmId = fcmId;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getMachineId() {
		return machineId;
	}


	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}


	public String getSdkVersion() {
		return sdkVersion;
	}


	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getManufaceturer() {
		return manufaceturer;
	}


	public void setManufaceturer(String manufaceturer) {
		this.manufaceturer = manufaceturer;
	}


	public String getGeoLocation() {
		return geoLocation;
	}


	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
