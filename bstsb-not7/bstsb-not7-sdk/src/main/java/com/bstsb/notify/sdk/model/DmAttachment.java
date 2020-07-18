/**
 * Copyright 2019. Bestinet Sdn Bhd
 */
package com.bstsb.notify.sdk.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * The Class DmAttachment.
 *
 * @author mary.jane
 * @since Jan 16, 2019
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
public class DmAttachment implements Attachment {

	/** The doc collection. */
	private String docCollection;

	/** The doc mgt id. */
	private String docMgtId;


	/**
	 * Gets the doc collection.
	 *
	 * @return the doc collection
	 */
	public String getDocCollection() {
		return docCollection;
	}


	/**
	 * Sets the doc collection.
	 *
	 * @param docCollection
	 *             the new doc collection
	 */
	public void setDocCollection(String docCollection) {
		this.docCollection = docCollection;
	}


	/**
	 * Gets the doc mgt id.
	 *
	 * @return the doc mgt id
	 */
	public String getDocMgtId() {
		return docMgtId;
	}


	/**
	 * Sets the doc mgt id.
	 *
	 * @param docMgtId
	 *             the new doc mgt id
	 */
	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}

}
