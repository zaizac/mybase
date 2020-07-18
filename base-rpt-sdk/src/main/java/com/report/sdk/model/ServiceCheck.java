/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.report.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mary Jane Buenaventura
 * @since 22/08/2016
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_EMPTY)
public class ServiceCheck {

	private String serviceResponse;
	private String serviceName;
	private String serviceUrl;
	private ServiceCheck ldap;
	private ServiceCheck mysql;
	private ServiceCheck mongodb;
	private ServiceCheck cache;
	private ServiceCheck idm;
	private ServiceCheck commons;
	private ServiceCheck notification;
	private ServiceCheck report;
	private ServiceCheck workflow;
	private ServiceCheck idmSvcTest;
	
	public ServiceCheck() {}
	
	public ServiceCheck(String serviceName, String serviceUrl, String serviceResponse) {
		this.serviceName = serviceName;
		this.serviceUrl = serviceUrl;
		this.serviceResponse = serviceResponse;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public ServiceCheck getLdap() {
		return ldap;
	}

	public void setLdap(ServiceCheck ldap) {
		this.ldap = ldap;
	}

	public ServiceCheck getMysql() {
		return mysql;
	}

	public void setMysql(ServiceCheck mysql) {
		this.mysql = mysql;
	}

	public ServiceCheck getMongodb() {
		return mongodb;
	}
	
	public void setMongodb(ServiceCheck mongodb) {
		this.mongodb = mongodb;
	}
	
	public ServiceCheck getCache() {
		return cache;
	}

	public void setCache(ServiceCheck cache) {
		this.cache = cache;
	}

	public ServiceCheck getIdm() {
		return idm;
	}

	public void setIdm(ServiceCheck idm) {
		this.idm = idm;
	}

	public ServiceCheck getCommons() {
		return commons;
	}

	public void setCommons(ServiceCheck commons) {
		this.commons = commons;
	}


	public ServiceCheck getNotification() {
		return notification;
	}

	public void setNotification(ServiceCheck notification) {
		this.notification = notification;
	}

	public ServiceCheck getReport() {
		return report;
	}

	public void setReport(ServiceCheck report) {
		this.report = report;
	}

	public ServiceCheck getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ServiceCheck workflow) {
		this.workflow = workflow;
	}

	public ServiceCheck getIdmSvcTest() {
		return idmSvcTest;
	}

	public void setIdmSvcTest(ServiceCheck idmSvcTest) {
		this.idmSvcTest = idmSvcTest;
	}

}