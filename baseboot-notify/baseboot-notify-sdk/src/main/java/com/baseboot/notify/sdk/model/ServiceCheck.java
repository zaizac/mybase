/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class ServiceCheck {

	private String serviceResponse;

	private String serviceName;

	private String serviceUrl;

	private ServiceCheck ldap;

	private ServiceCheck mysql;

	private ServiceCheck cache;

	private ServiceCheck idm;

	private ServiceCheck commons;

	private ServiceCheck embassy;

	private ServiceCheck equota;

	private ServiceCheck erunaway;

	private ServiceCheck dm;

	private ServiceCheck fwcmsGw;

	private ServiceCheck myimmsGw;

	private ServiceCheck notification;

	private ServiceCheck plks;

	private ServiceCheck pmg;

	private ServiceCheck report;

	private ServiceCheck sim;

	private ServiceCheck workflow;

	private ServiceCheck idmSvcTest;


	public ServiceCheck() {
	}


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


	public ServiceCheck getEmbassy() {
		return embassy;
	}


	public void setEmbassy(ServiceCheck embassy) {
		this.embassy = embassy;
	}


	public ServiceCheck getEquota() {
		return equota;
	}


	public void setEquota(ServiceCheck equota) {
		this.equota = equota;
	}


	public ServiceCheck getErunaway() {
		return erunaway;
	}


	public void setErunaway(ServiceCheck erunaway) {
		this.erunaway = erunaway;
	}


	public ServiceCheck getDm() {
		return dm;
	}


	public void setDm(ServiceCheck dm) {
		this.dm = dm;
	}


	public ServiceCheck getFwcmsGw() {
		return fwcmsGw;
	}


	public void setFwcmsGw(ServiceCheck fwcmsGw) {
		this.fwcmsGw = fwcmsGw;
	}


	public ServiceCheck getMyimmsGw() {
		return myimmsGw;
	}


	public void setMyimmsGw(ServiceCheck myimmsGw) {
		this.myimmsGw = myimmsGw;
	}


	public ServiceCheck getNotification() {
		return notification;
	}


	public void setNotification(ServiceCheck notification) {
		this.notification = notification;
	}


	public ServiceCheck getPlks() {
		return plks;
	}


	public void setPlks(ServiceCheck plks) {
		this.plks = plks;
	}


	public ServiceCheck getPmg() {
		return pmg;
	}


	public void setPmg(ServiceCheck pmg) {
		this.pmg = pmg;
	}


	public ServiceCheck getReport() {
		return report;
	}


	public void setReport(ServiceCheck report) {
		this.report = report;
	}


	public ServiceCheck getSim() {
		return sim;
	}


	public void setSim(ServiceCheck sim) {
		this.sim = sim;
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