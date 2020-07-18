/**
 * Copyright 2019. 
 */
package com.util.model;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.BaseUtil;
import com.util.constants.BaseConstants;
import com.util.serializer.JsonTimestampSerializer;


/**
 * The Class Device.
 *
 * @author mary.jane
 * @since Nov 12, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Device implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4910741561295224472L;

	private String fcmToken;
	
	/** The machine id. */
	private String machineId;

	/** The sdk version. */
	private String sdkVersion;

	/** The model. */
	private String model;

	/** The brand. */
	private String brand;

	/** The manufacturer. */
	private String manufacturer;

	/** The geo location. */
	private String geoLocation;

	/** The location name. */
	private String locationName;

	/** The ip address. */
	private String ipAddress;

	/** The update dt. */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	private Timestamp updateDt;

	/**
	 * Instantiates a new device.
	 */
	public Device() {
		// DO NOTHING
	}

	

	/**
	 * @return the fcmToken
	 */
	public String getFcmToken() {
		return fcmToken;
	}




	/**
	 * @param fcmToken the fcmToken to set
	 */
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}




	/**
	 * Gets the machine id.
	 *
	 * @return the machine id
	 */
	public String getMachineId() {
		return machineId;
	}


	/**
	 * Sets the machine id.
	 *
	 * @param machineId the new machine id
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}


	/**
	 * Gets the sdk version.
	 *
	 * @return the sdk version
	 */
	public String getSdkVersion() {
		return sdkVersion;
	}


	/**
	 * Sets the sdk version.
	 *
	 * @param sdkVersion the new sdk version
	 */
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}


	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}


	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}


	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}


	/**
	 * Sets the brand.
	 *
	 * @param brand the new brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}


	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}


	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer the new manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	/**
	 * Gets the geo location.
	 *
	 * @return the geo location
	 */
	public String getGeoLocation() {
		return geoLocation;
	}


	/**
	 * Sets the geo location.
	 *
	 * @param geoLocation the new geo location
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}


	/**
	 * Gets the location name.
	 *
	 * @return the location name
	 */
	public String getLocationName() {
		return BaseUtil.getStrUpperWithNull(locationName);
	}


	/**
	 * Sets the location name.
	 *
	 * @param locationName the new location name
	 */
	public void setLocationName(String locationName) {
		this.locationName = BaseUtil.getStrUpperWithNull(locationName);
	}


	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}


	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	/**
	 * Gets the update dt.
	 *
	 * @return the update dt
	 */
	public Timestamp getUpdateDt() {
		return updateDt;
	}


	/**
	 * Sets the update dt.
	 *
	 * @param updateDt the new update dt
	 */
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}

}
