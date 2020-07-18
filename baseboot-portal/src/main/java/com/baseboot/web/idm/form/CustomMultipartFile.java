/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.form;


import java.io.Serializable;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 4, 2016
 */
public class CustomMultipartFile implements Serializable {

	private static final long serialVersionUID = 6504304054277314612L;

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
