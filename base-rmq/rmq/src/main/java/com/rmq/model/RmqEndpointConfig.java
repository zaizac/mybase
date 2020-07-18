/**
 * 
 */
package com.rmq.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author mary.jane
 *
 */

@Entity
@Table(name = "RMQ_ENDPOINT_CONFIG")
public class RmqEndpointConfig  implements Serializable {

	private static final long serialVersionUID = -7554821781929227941L;

	@Id
	@Column(name = "ENDPOINT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "QUEUE_NAME")
	private String queueName;
	
	@Column(name = "QUEUE_EXCHANGE")
	private String queueExchange;
	
	@Column(name = "MODULE")
	private String module;
	
	@Column(name = "ACTION")
	private String action;
	
	@Column(name = "HTTP_METHOD")
	private String httpMethod;
	
	@Column(name = "REDIRECT_URL")
	private String redirectUrl;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "`STATUS`")
	private Boolean status;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	/**
	 * @return the queueExchange
	 */
	public String getQueueExchange() {
		return queueExchange;
	}

	/**
	 * @param queueExchange the queueExchange to set
	 */
	public void setQueueExchange(String queueExchange) {
		this.queueExchange = queueExchange;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the redirectUrl
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * @param redirectUrl the redirectUrl to set
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the createDt
	 */
	public Timestamp getCreateDt() {
		return createDt;
	}

	/**
	 * @param createDt the createDt to set
	 */
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}
	
	
	
}
