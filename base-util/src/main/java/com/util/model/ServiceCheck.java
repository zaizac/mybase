/**
 * Copyright 2019. 
 */
package com.util.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class ServiceCheck.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class ServiceCheck {

	/** The service response. */
	private String serviceResponse;

	/** The service name. */
	private String serviceName;

	/** The service url. */
	private String serviceUrl;

	/** The ldap. */
	private ServiceCheck ldap;

	/** The mysql. */
	private ServiceCheck mysql;

	/** The mongodb. */
	private ServiceCheck mongodb;

	/** The cache. */
	private ServiceCheck cache;

	/** The idm. */
	private ServiceCheck idm;

	/** The commons. */
	private ServiceCheck commons;

	/** The embassy. */
	private ServiceCheck embassy;

	/** The equota. */
	private ServiceCheck equota;

	/** The dm. */
	private ServiceCheck dm;

	/** The notification. */
	private ServiceCheck notification;

	/** The report. */
	private ServiceCheck report;

	/** The workflow. */
	private ServiceCheck workflow;

	/** The idm svc test. */
	private ServiceCheck idmSvcTest;


	/**
	 * Instantiates a new service check.
	 */
	public ServiceCheck() {
	}


	/**
	 * Instantiates a new service check.
	 *
	 * @param serviceName the service name
	 * @param serviceUrl the service url
	 * @param serviceResponse the service response
	 */
	public ServiceCheck(String serviceName, String serviceUrl, String serviceResponse) {
		this.serviceName = serviceName;
		this.serviceUrl = serviceUrl;
		this.serviceResponse = serviceResponse;
	}


	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}


	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	/**
	 * Gets the service url.
	 *
	 * @return the service url
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}


	/**
	 * Sets the service url.
	 *
	 * @param serviceUrl the new service url
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}


	/**
	 * Gets the service response.
	 *
	 * @return the service response
	 */
	public String getServiceResponse() {
		return serviceResponse;
	}


	/**
	 * Sets the service response.
	 *
	 * @param serviceResponse the new service response
	 */
	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}


	/**
	 * Gets the ldap.
	 *
	 * @return the ldap
	 */
	public ServiceCheck getLdap() {
		return ldap;
	}


	/**
	 * Sets the ldap.
	 *
	 * @param ldap the new ldap
	 */
	public void setLdap(ServiceCheck ldap) {
		this.ldap = ldap;
	}


	/**
	 * Gets the mysql.
	 *
	 * @return the mysql
	 */
	public ServiceCheck getMysql() {
		return mysql;
	}


	/**
	 * Sets the mysql.
	 *
	 * @param mysql the new mysql
	 */
	public void setMysql(ServiceCheck mysql) {
		this.mysql = mysql;
	}


	/**
	 * Gets the mongodb.
	 *
	 * @return the mongodb
	 */
	public ServiceCheck getMongodb() {
		return mongodb;
	}


	/**
	 * Sets the mongodb.
	 *
	 * @param mongodb the new mongodb
	 */
	public void setMongodb(ServiceCheck mongodb) {
		this.mongodb = mongodb;
	}


	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	public ServiceCheck getCache() {
		return cache;
	}


	/**
	 * Sets the cache.
	 *
	 * @param cache the new cache
	 */
	public void setCache(ServiceCheck cache) {
		this.cache = cache;
	}


	/**
	 * Gets the idm.
	 *
	 * @return the idm
	 */
	public ServiceCheck getIdm() {
		return idm;
	}


	/**
	 * Sets the idm.
	 *
	 * @param idm the new idm
	 */
	public void setIdm(ServiceCheck idm) {
		this.idm = idm;
	}


	/**
	 * Gets the commons.
	 *
	 * @return the commons
	 */
	public ServiceCheck getCommons() {
		return commons;
	}


	/**
	 * Sets the commons.
	 *
	 * @param commons the new commons
	 */
	public void setCommons(ServiceCheck commons) {
		this.commons = commons;
	}


	/**
	 * Gets the embassy.
	 *
	 * @return the embassy
	 */
	public ServiceCheck getEmbassy() {
		return embassy;
	}


	/**
	 * Sets the embassy.
	 *
	 * @param embassy the new embassy
	 */
	public void setEmbassy(ServiceCheck embassy) {
		this.embassy = embassy;
	}


	/**
	 * Gets the equota.
	 *
	 * @return the equota
	 */
	public ServiceCheck getEquota() {
		return equota;
	}


	/**
	 * Sets the equota.
	 *
	 * @param equota the new equota
	 */
	public void setEquota(ServiceCheck equota) {
		this.equota = equota;
	}


	/**
	 * Gets the dm.
	 *
	 * @return the dm
	 */
	public ServiceCheck getDm() {
		return dm;
	}


	/**
	 * Sets the dm.
	 *
	 * @param dm the new dm
	 */
	public void setDm(ServiceCheck dm) {
		this.dm = dm;
	}


	/**
	 * Gets the notification.
	 *
	 * @return the notification
	 */
	public ServiceCheck getNotification() {
		return notification;
	}


	/**
	 * Sets the notification.
	 *
	 * @param notification the new notification
	 */
	public void setNotification(ServiceCheck notification) {
		this.notification = notification;
	}


	/**
	 * Gets the report.
	 *
	 * @return the report
	 */
	public ServiceCheck getReport() {
		return report;
	}


	/**
	 * Sets the report.
	 *
	 * @param report the new report
	 */
	public void setReport(ServiceCheck report) {
		this.report = report;
	}


	/**
	 * Gets the workflow.
	 *
	 * @return the workflow
	 */
	public ServiceCheck getWorkflow() {
		return workflow;
	}


	/**
	 * Sets the workflow.
	 *
	 * @param workflow the new workflow
	 */
	public void setWorkflow(ServiceCheck workflow) {
		this.workflow = workflow;
	}


	/**
	 * Gets the idm svc test.
	 *
	 * @return the idm svc test
	 */
	public ServiceCheck getIdmSvcTest() {
		return idmSvcTest;
	}


	/**
	 * Sets the idm svc test.
	 *
	 * @param idmSvcTest the new idm svc test
	 */
	public void setIdmSvcTest(ServiceCheck idmSvcTest) {
		this.idmSvcTest = idmSvcTest;
	}

}