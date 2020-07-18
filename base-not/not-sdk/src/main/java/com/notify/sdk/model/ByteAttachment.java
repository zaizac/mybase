/**
 * Copyright 2019. 
 */
package com.notify.sdk.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * The Class ByteAttachment.
 *
 * @author mary.jane
 * @since Jan 16, 2019
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
public class ByteAttachment implements Attachment {

	/** The file name. */
	private String fileName;

	/** The file. */
	private byte[] file;

	/** The mime type. */
	private String mimeType;


	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * Sets the file name.
	 *
	 * @param fileName
	 *             the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}


	/**
	 * Sets the file.
	 *
	 * @param file
	 *             the new file
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}


	/**
	 * Gets the mime type.
	 *
	 * @return the mime type
	 */
	public String getMimeType() {
		return mimeType;
	}


	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *             the new mime type
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
