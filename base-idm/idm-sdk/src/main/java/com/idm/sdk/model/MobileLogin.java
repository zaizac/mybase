package com.idm.sdk.model;


import com.util.model.Device;


/**
 * @author mary.jane
 * @since Jan 18, 2019
 */
public class MobileLogin {

	private String userId;

	private String password;

	private Device deviceInfo;


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


	public Device getDeviceInfo() {
		return deviceInfo;
	}


	public void setDeviceInfo(Device deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
