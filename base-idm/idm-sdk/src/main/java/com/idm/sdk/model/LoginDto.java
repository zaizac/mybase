package com.idm.sdk.model;

import java.io.Serializable;

import com.util.model.Device;

/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class LoginDto implements Serializable {

	private static final long serialVersionUID = 5073006754495008086L;

	private String userId;

	private String password;

	private String fwcms;

	private String firstName;

	private String lastName;

	private String clientId;

	private String clientSecret;

	private String userType;

	private String portalType;

	private Token token;

	private String status;

	private String userRoleGroupCode;

	private String gender;

	private Integer profId;

	private Integer branchId;

	private String cntry;

	private String systemType;

	private Device device;
	
	private boolean forceDisableDevice;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFwcms() {
		return fwcms;
	}

	public void setFwcms(String fwcms) {
		this.fwcms = fwcms;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRoleGroupCode() {
		return userRoleGroupCode;
	}

	public void setUserRoleGroupCode(String userRoleGroupCode) {
		this.userRoleGroupCode = userRoleGroupCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getProfId() {
		return profId;
	}

	public void setProfId(Integer profId) {
		this.profId = profId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getCntry() {
		return cntry;
	}

	public void setCntry(String cntry) {
		this.cntry = cntry;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public boolean isForceDisableDevice() {
		return forceDisableDevice;
	}
	
	public void setForceDisableDevice(boolean forceDisableDevice) {
		this.forceDisableDevice = forceDisableDevice;
	}
}
