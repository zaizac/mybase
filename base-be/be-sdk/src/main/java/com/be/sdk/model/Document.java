package com.be.sdk.model;


import java.io.Serializable;
import java.sql.Timestamp;


public class Document implements Serializable, IQfCriteria<Document> {

	private static final long serialVersionUID = 1218743406159472639L;

	private Integer docId;

	private String dmBucket;

	private String docTrxnNo;

	private String docDesc;

	private Integer order;

	private String type;

	private Integer size;

	private boolean compulsary;

	private boolean dimensionCompulsary;

	private Integer maxHeight;

	private Integer maxWidth;

	private Integer minHeight;

	private Integer minWidth;

	private Timestamp createDt;

	private String createId;

	private Timestamp updateDt;

	private String updateId;

	private Integer maxDocCnt;


	public Integer getDocId() {
		return docId;
	}


	public void setDocId(Integer docId) {
		this.docId = docId;
	}


	public String getDmBucket() {
		return dmBucket;
	}


	public void setDmBucket(String dmBucket) {
		this.dmBucket = dmBucket;
	}


	public String getDocTrxnNo() {
		return docTrxnNo;
	}


	public void setDocTrxnNo(String docTrxnNo) {
		this.docTrxnNo = docTrxnNo;
	}


	public String getDocDesc() {
		return docDesc;
	}


	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}


	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Integer getSize() {
		return size;
	}


	public void setSize(Integer size) {
		this.size = size;
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


	public Integer getMaxHeight() {
		return maxHeight;
	}


	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}


	public Integer getMaxWidth() {
		return maxWidth;
	}


	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}


	public Integer getMinHeight() {
		return minHeight;
	}


	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}


	public Integer getMinWidth() {
		return minWidth;
	}


	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}


	public Timestamp getCreateDt() {
		return createDt;
	}


	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


	public String getCreateId() {
		return createId;
	}


	public void setCreateId(String createId) {
		this.createId = createId;
	}


	public Timestamp getUpdateDt() {
		return updateDt;
	}


	public void setUpdateDt(Timestamp updateDt) {
		this.updateDt = updateDt;
	}


	public String getUpdateId() {
		return updateId;
	}


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
