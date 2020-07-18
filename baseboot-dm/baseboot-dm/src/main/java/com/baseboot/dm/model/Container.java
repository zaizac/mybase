/**
 * Copyright 2018. Bestinet Sdn. Bhd.
 */
package com.baseboot.dm.model;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.baseboot.dm.core.AbstractEntity;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
@Document(collection = "#{T(com.baseboot.dm.util.TenantGenerator).tenant()}_CONTAINER.chunks")
public class Container extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4279302861499829607L;

	@Id
	private String id;

	private String docId;

	private String refNo;

	private byte[] content;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDocId() {
		return docId;
	}


	public void setDocId(String docId) {
		this.docId = docId;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}

}