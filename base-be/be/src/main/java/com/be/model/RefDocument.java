package com.be.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.be.core.AbstractEntity;
import com.be.sdk.model.IQfCriteria;


/**
 * The persistent class for the REF_DOCUMENTS database table.
 * 
 */
@Entity
@Table(name = "REF_DOCUMENTS")
@NamedQuery(name = "RefDocument.findAll", query = "SELECT r FROM RefDocument r")
public class RefDocument extends AbstractEntity implements Serializable, IQfCriteria<RefDocument> {

	private static final long serialVersionUID = 8684018224128725778L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DOC_ID")
	private Integer docId;

	@Column(name = "DM_BUCKET")
	private String dmBucket;

	@Column(name = "DOC_TRXN_NO")
	private String docTrxnNo;

	@Lob
	@Column(name = "DOC_DESC")
	private String docDesc;

	@Column(name = "`ORDER`")
	private Integer order;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "SIZE")
	private Integer size;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "COMPULSARY")
	private boolean compulsary;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "DIMENSION_COMPULSARY")
	private boolean dimensionCompulsary;

	@Column(name = "MAX_HEIGHT")
	private Integer maxHeight;

	@Column(name = "MAX_WIDTH")
	private Integer maxWidth;

	@Column(name = "MIN_HEIGHT")
	private Integer minHeight;

	@Column(name = "MIN_WIDTH")
	private Integer minWidth;

	@Column(name = "CREATE_DT")
	private Timestamp createDt;

	@Column(name = "CREATE_ID")
	private String createId;

	@Column(name = "UPDATE_DT")
	private Timestamp updateDt;

	@Column(name = "UPDATE_ID")
	private String updateId;

	@Column(name = "MAX_DOC_CNT")
	private Integer maxDocCnt;
	
	public RefDocument() {
	}


	public Integer getDocId() {
		return this.docId;
	}


	public void setDocId(Integer docId) {
		this.docId = docId;
	}


	@Override
	public Timestamp getCreateDt() {
		return this.createDt;
	}


	@Override
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	@Override
	public String getCreateId() {
		return this.createId;
	}


	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public boolean isCompulsary() {
		return compulsary;
	}


	public void setCompulsary(boolean compulsary) {
		this.compulsary = compulsary;
	}


	public boolean isDimensionCompulsary() {
		return dimensionCompulsary;
	}


	public void setDimensionCompulsary(boolean dimensionCompulsary) {
		this.dimensionCompulsary = dimensionCompulsary;
	}


	public String getDmBucket() {
		return this.dmBucket;
	}


	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}


	public String getDocDesc() {
		return this.docDesc;
	}


	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}


	public String getDocTrxnNo() {
		return this.docTrxnNo;
	}


	public void setDocTrxnNo(String docTrxnNo) {
		this.docTrxnNo = docTrxnNo;
	}


	public Integer getMaxHeight() {
		return this.maxHeight;
	}


	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}


	public Integer getMaxWidth() {
		return this.maxWidth;
	}


	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}


	public Integer getMinHeight() {
		return this.minHeight;
	}


	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}


	public Integer getMinWidth() {
		return this.minWidth;
	}


	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}


	public Integer getOrder() {
		return this.order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public Integer getSize() {
		return this.size;
	}


	public void setSize(Integer size) {
		this.size = size;
	}


	public String getType() {
		return this.type;
	}


	public void setType(String type) {
		this.type = type;
	}


	@Override
	public Timestamp getUpdateDt() {
		return this.updateDt;
	}


	@Override
	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	@Override
	public String getUpdateId() {
		return this.updateId;
	}


	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}


	
	public Integer getMaxDocCnt() {
		return maxDocCnt;
	}


	
	public void setMaxDocCnt(Integer maxDocCnt) {
		this.maxDocCnt = maxDocCnt;
	}

}