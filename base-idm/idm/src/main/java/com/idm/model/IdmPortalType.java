package com.idm.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
 * The persistent class for the IDM_PORTAL_TYPE database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 30, 2019
 */
@Entity
@Table(name = "IDM_PORTAL_TYPE")
@NamedQuery(name = "IdmPortalType.findAll", query = "SELECT i FROM IdmPortalType i")
public class IdmPortalType extends AbstractEntity implements Serializable, IQfCriteria<IdmPortalType> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PORTAL_TYPE_CODE", unique = true, nullable = false, length = 10)
	private String portalTypeCode;

	@Column(name = "PORTAL_TYPE_DESC")
	@Convert(converter = LangDescConverter.class)
	private LangDesc portalTypeDesc;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT", nullable = false)
	private Timestamp createDt;

	@Column(name = "CREATE_ID", length = 20)
	private String createId;

	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT", nullable = false)
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID", length = 20)
	private String updateId;


	public IdmPortalType() {
		// DO NOTHING
	}


	public IdmPortalType(String portalTypeCode) {
		this.portalTypeCode = portalTypeCode;
	}


	public String getPortalTypeCode() {
		return portalTypeCode;
	}


	public void setPortalTypeCode(String portalTypeCode) {
		this.portalTypeCode = portalTypeCode;
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


	public LangDesc getPortalTypeDesc() {
		return portalTypeDesc;
	}


	public void setPortalTypeDesc(LangDesc portalTypeDesc) {
		this.portalTypeDesc = portalTypeDesc;
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