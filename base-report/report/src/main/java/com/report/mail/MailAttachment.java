/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.report.mail;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mary Jane Buenaventura
 * @since 09/06/2016
 */
@JsonInclude(Include.NON_NULL)
public class MailAttachment  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;
	private byte[] file;
	
	public MailAttachment() {}
	
	public MailAttachment(String fileName) {this.fileName=fileName;}
	
	public MailAttachment(String fileName,byte[] file) {
		this.fileName=fileName;
		this.file=file;
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
	
}
