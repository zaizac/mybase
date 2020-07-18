/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.sdk.model;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public class CustomMultipartFile implements Serializable {

	private static final long serialVersionUID = -6039839119041524047L;

	private String filename;

	private long size;

	private String contentType;

	private byte[] content;


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public long getSize() {
		return size;
	}


	public void setSize(long size) {
		this.size = size;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}

}
