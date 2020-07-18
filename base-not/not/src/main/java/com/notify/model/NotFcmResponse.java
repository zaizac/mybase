/**
 * 
 */
package com.notify.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.notify.core.AbstractEntity;
import com.util.constants.BaseConstants;
import com.util.model.IQfCriteria;
import com.util.serializer.JsonTimestampSerializer;

/**
 * @author mary.jane
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "NOT_FCM_RESPONSE")
public class NotFcmResponse extends AbstractEntity implements Serializable, IQfCriteria<NotFcmResponse> {

	private static final long serialVersionUID = 2227482202878113086L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FCM_RESP_ID")
	private Integer id;
	
	@Column(name = "NOTIFY_ID")
	private Integer notifyId;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "MULTICAST_ID")
	private String multicastId;
	
	@Column(name = "SUCCESS")
	private Integer success;
	
	@Column(name = "FAILURE")
	private Integer failure;
	
	@Column(name = "CANONICAL_ID")
	private Integer canonicalId;
	
	@Column(name = "RESULTS")
	private String results;
	
	/** The create dt. */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	/** The create id. */
	@Column(name = "CREATE_ID")
	private String createId;

	/** The update dt. */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstants.DT_DD_MM_YYYY_SLASH_TIME_A)
	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;
	
	
	public NotFcmResponse() {
		// DO NOTHING
	}
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the notifyId
	 */
	public Integer getNotifyId() {
		return notifyId;
	}

	/**
	 * @param notifyId the notifyId to set
	 */
	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the multicastId
	 */
	public String getMulticastId() {
		return multicastId;
	}

	/**
	 * @param multicastId the multicastId to set
	 */
	public void setMulticastId(String multicastId) {
		this.multicastId = multicastId;
	}

	/**
	 * @return the success
	 */
	public Integer getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Integer success) {
		this.success = success;
	}

	/**
	 * @return the failure
	 */
	public Integer getFailure() {
		return failure;
	}

	/**
	 * @param failure the failure to set
	 */
	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	/**
	 * @return the canonicalId
	 */
	public Integer getCanonicalId() {
		return canonicalId;
	}

	/**
	 * @param canonicalId the canonicalId to set
	 */
	public void setCanonicalId(Integer canonicalId) {
		this.canonicalId = canonicalId;
	}

	/**
	 * @return the results
	 */
	public String getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(String results) {
		this.results = results;
	}



	/** The update id. */
	@Column(name = "UPDATE_ID")
	private String updateId;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#getCreateDt()
	 */
	@Override
	public Timestamp getCreateDt() {
		return createDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#setCreateDt(java.sql.Timestamp)
	 */
	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#getCreateId()
	 */
	@Override
	public String getCreateId() {
		return createId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#setCreateId(java.lang.String)
	 */
	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#getUpdateDt()
	 */
	@Override
	public Timestamp getUpdateDt() {
		return updateDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#setUpdateDt(java.sql.Timestamp)
	 */
	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#getUpdateId()
	 */
	@Override
	public String getUpdateId() {
		return updateId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.notify.core.AbstractEntity#setUpdateId(java.lang.String)
	 */
	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


}
