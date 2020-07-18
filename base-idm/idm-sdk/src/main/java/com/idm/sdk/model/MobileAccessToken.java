package com.idm.sdk.model;


import com.util.model.Device;


/**
 * @author mary.jane
 * @since Dec 31, 2018
 */
public class MobileAccessToken {

	private String userId;

	private String accessToken;

	private Device deviceInfo;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public Device getDeviceInfo() {
		return deviceInfo;
	}


	public void setDeviceInfo(Device deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
