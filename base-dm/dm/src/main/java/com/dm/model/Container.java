/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.dm.model;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The Class Container.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Document(collection = "#{T(com.dm.util.TenantGenerator).TENANT}_CONTAINER.chunks")
public class Container implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4279302861499829607L;

	/** The id. */
	@Id
	private String id;

	/** The doc id. */
	private String docId;

	/** The ref no. */
	private String refNo;

	/** The content. */
	private byte[] content;


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * Gets the doc id.
	 *
	 * @return the doc id
	 */
	public String getDocId() {
		return docId;
	}


	/**
	 * Sets the doc id.
	 *
	 * @param docId the new doc id
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}


	/**
	 * Gets the ref no.
	 *
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}


	/**
	 * Sets the ref no.
	 *
	 * @param refNo the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
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