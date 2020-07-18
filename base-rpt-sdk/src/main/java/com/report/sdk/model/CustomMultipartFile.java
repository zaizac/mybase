package com.report.sdk.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Naem Othman
 * @since Oct 21 , 2015
 */

@JsonInclude(Include.NON_NULL)
public class CustomMultipartFile implements Serializable{
	
	private static final long serialVersionUID = -7248101515569034263L;
	
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
