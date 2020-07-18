package com.be.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the BE_TRXN_DOCUMENTS database table.
 *
 * @author Mary Jane Buenaventura
 * @since Oct 5, 2019
 */
@Embeddable
public class BeTrxnDocumentPK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "DOC_REF_NO")
	private String docRefNo;

	@Column(name = "DOC_ID")
	private int docId;

	@Column(name = "DOC_MGT_ID")
	private String docMgtId;


	public String getDocRefNo() {
		return docRefNo;
	}


	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}


	public int getDocId() {
		return docId;
	}


	public void setDocId(int docId) {
		this.docId = docId;
	}


	public String getDocMgtId() {
		return docMgtId;
	}


	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
	}


	public BeTrxnDocumentPK() {
	}


	/**
	 * @param docRefNo
	 * @param docId
	 * @param docMgtId
	 */
	public BeTrxnDocumentPK(String docRefNo, int docId, String docMgtId) {
		super();
		this.docRefNo = docRefNo;
		this.docId = docId;
		this.docMgtId = docMgtId;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BeTrxnDocumentPK)) {
			return false;
		}
		BeTrxnDocumentPK castOther = (BeTrxnDocumentPK) other;
		return docRefNo.equals(castOther.docRefNo) && (docId == castOther.docId)
				&& docMgtId.equals(castOther.docMgtId);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + docRefNo.hashCode();
		hash = hash * prime + docId;
		hash = hash * prime + docMgtId.hashCode();

		return hash;
	}
}