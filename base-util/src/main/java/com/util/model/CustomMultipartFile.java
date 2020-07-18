/**
 * Copyright 2019. 
 */
package com.util.model;


import java.io.Serializable;


/**
 * The Class CustomMultipartFile.
 *
 * @author Mary Jane Buenaventura
 * @since May 18, 2018
 */
public class CustomMultipartFile implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6058102772778256646L;

	/** The filename. */
	private String filename;

	/** The size. */
	private long size;

	/** The content type. */
	private String contentType;

	/** The content. */
	private byte[] content;


	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}


	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public long getSize() {
		return size;
	}


	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(long size) {
		this.size = size;
	}


	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}


	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}


	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

}