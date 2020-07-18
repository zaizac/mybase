/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.sdk.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@JsonInclude(Include.NON_NULL)
public class MailAttachment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;

	private byte[] file;

	private String mimeType;


	public MailAttachment() {
	}


	public MailAttachment(String fileName, byte[] file, String mimeType) {
		this.fileName = fileName;
		this.file = file;
		this.mimeType = mimeType;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public byte[] getFile() {
		return file;
	}


	public void setFile(byte[] file) {
		this.file = file;
	}


	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	public String getMimeType() {
		return mimeType;
	}

}
