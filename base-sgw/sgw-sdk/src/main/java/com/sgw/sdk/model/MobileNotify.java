/**
 * 
 */
package com.sgw.sdk.model;

import com.util.model.Device;

/**
 * @author mary.jane
 *
 */
public class MobileNotify {

	String notifyTo;
	
	String locale;
	
	Device device;

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
}
