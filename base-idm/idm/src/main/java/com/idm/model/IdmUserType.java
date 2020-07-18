package com.idm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.idm.core.AbstractEntity;
import com.idm.util.LangDescConverter;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.model.LangDesc;
import com.util.serializer.JsonTimestampDeserializer;
import com.util.serializer.JsonTimestampSerializer;

/**
 * The persistent class for the IDM_USER_TYPE database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_USER_TYPE")
@NamedQuery(name = "IdmUserType.findAll", query = "SELECT i FROM IdmUserType i")
public class IdmUserType extends AbstractEntity implements Serializable, IQfCriteria<IdmUserType> {

	private static final long serialVersionUID = 8513477949752093410L;

	@Id
	@Column(name = "USER_TYPE_CODE", unique = true, nullable = false, length = 10)
	private String userTypeCode;

	@Column(name = "USER_TYPE_DESC")
	@Convert(converter = LangDescConverter.class)
	private LangDesc userTypeDesc;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "EMAIL_AS_USER_ID")
	private boolean emailAsUserId;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "BYPASS_PASSWORD")
	private boolean bypassPword;

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

	public IdmUserType() {
		// DO NOTHING
	}

	public IdmUserType(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public LangDesc getUserTypeDesc() {
		return userTypeDesc;
	}

	public void setUserTypeDesc(LangDesc userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
	}

	public void setEmailAsUserId(boolean emailAsUserId) {
		this.emailAsUserId = emailAsUserId;
	}

	public boolean getEmailAsUserId() {
		return emailAsUserId;
	}

	/**
	 * @return the bypassPword
	 */
	public boolean isBypassPword() {
		return bypassPword;
	}

	/**
	 * @param bypassPword the bypassPword to set
	 */
	public void setBypassPword(boolean bypassPword) {
		this.bypassPword = bypassPword;
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
