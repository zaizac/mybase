/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.form;

/**
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class ImageSizeConfig {

	private int minHeight;

	private int maxHeight;

	private int minWidth;

	private int maxWidth;


	public ImageSizeConfig() {
		super();
	}


	public ImageSizeConfig(int minHeight, int maxHeight, int minWidth, int maxWidth) {
		super();
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
	}


	public int getMinHeight() {
		return minHeight;
	}


	public int getMaxHeight() {
		return maxHeight;
	}


	public int getMinWidth() {
		return minWidth;
	}


	public int getMaxWidth() {
		return maxWidth;
	}


	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}


	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}


	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}


	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

}
