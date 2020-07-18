package com.idm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;

/**
 * The persistent class for the IDM_FCM_DEVICE database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@DynamicUpdate
@Table(name = "IDM_FCM_DEVICE")
@NamedQuery(name = "IdmFcmDevice.findAll", query = "SELECT i FROM IdmFcmDevice i")
public class IdmFcmDevice extends AbstractEntity implements Serializable, IQfCriteria<IdmFcmDevice> {

	private static final long serialVersionUID = -7868730036043878155L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEVICE_ID", unique = true, nullable = false)
	private Integer deviceId;

//	@Column(name = "FCM_ID", nullable = false)
//	private int fcmId;

	@JsonIgnoreProperties(value = "idmFcmDevices")
	@ManyToOne(targetEntity = IdmFcm.class)
	@JoinColumn(name = "FCM_ID", referencedColumnName = "FCM_ID")
	public IdmFcm fcm;

	@Column(name = "FCM_TOKEN", length = 250)
	private String fcmToken;

	@Column(name = "MACHINE_ID", length = 250)
	private String machineId;

	@Column(name = "SDK_VERSION", nullable = false, length = 45)
	private String sdkVersion;

	@Column(name = "MODEL", length = 250)
	private String model;

	@Column(name = "BRAND", length = 250)
	private String brand;

	@Column(name = "MANUFACTURER", length = 250)
	private String manufacturer;

	@Column(name = "GEO_LOCATION", length = 250)
	private String geoLocation;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "`STATUS`")
	private Boolean status;

	@Column(name = "CREATE_ID", length = 100)
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "UPDATE_ID", length = 100)
	private String updateId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;

	public IdmFcmDevice() {
		// DO NOTHING
	}
	
//	@PostConstruct
//	protected void postConstruct() {
//		if(!BaseUtil.isObjNull(fcm)) {
//			fcm.setFcmDevices(null);
//		}
//	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

//	public int getFcmId() {
//		return fcmId;
//	}
//
//
//	public void setFcmId(int fcmId) {
//		this.fcmId = fcmId;
//	}

	public IdmFcm getFcm() {
		return fcm;
	}
	
	public void setFcm(IdmFcm fcm) {
		this.fcm = fcm;
	}

	public String getFcmToken() {
		return this.fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
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