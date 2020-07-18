package com.idm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * The persistent class for the IDM_FCM database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@DynamicUpdate
@Table(name = "IDM_FCM")
@NamedQuery(name = "IdmFcm.findAll", query = "SELECT i FROM IdmFcm i")
public class IdmFcm extends AbstractEntity implements Serializable, IQfCriteria<IdmFcm> {

	private static final long serialVersionUID = -5963197181754072146L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FCM_ID", unique = true, nullable = false)
	private Integer fcmId;

	@Column(name = "USER_ID", nullable = false, length = 100)
	private String userId;

	@JsonIgnoreProperties(value = "fcm")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private IdmProfile idmProfile;
	
	@Column(name = "PIN_CD", length = 250)
	private String pinCd;

	@Column(name = "FACE_ID", length = 250)
	private String faceId;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "`STATUS`")
	private Boolean status;

	@JsonIgnoreProperties(value = "fcm", allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "fcm", orphanRemoval = true)
	private List<IdmFcmDevice> fcmDevices;

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

	public IdmFcm() {
		// DO NOTHING
	}

	public IdmFcm(Integer fcmId) {
		this.fcmId = fcmId;
	}

	public Integer getFcmId() {
		return fcmId;
	}

	public void setFcmId(Integer fcmId) {
		this.fcmId = fcmId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public IdmProfile getIdmProfile() {
		return idmProfile;
	}
	
	public void setIdmProfile(IdmProfile idmProfile) {
		this.idmProfile = idmProfile;
	}

	public String getPinCd() {
		return pinCd;
	}

	public void setPinCd(String pinCd) {
		this.pinCd = pinCd;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<IdmFcmDevice> getFcmDevices() {
		return fcmDevices;
	}

	public void setFcmDevices(List<IdmFcmDevice> fcmDevices) {
		this.fcmDevices = fcmDevices;
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
