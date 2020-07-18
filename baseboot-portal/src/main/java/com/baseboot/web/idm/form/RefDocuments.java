/**
 * Copyright 2016. Bestinet Sdn Bhd
 */
package com.baseboot.web.idm.form;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Amirah binti Ibrahim
 * @since July 7, 2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RefDocuments implements Serializable {

	private static final long serialVersionUID = 1218743406159472639L;

	private Integer docId;

	private String trxnNo;

	private String title;

	private String type;

	private int size;

	private String compulsary;

	private String docDescEn;

	private String docDescMy;

	private int sortOrder;

	private Integer minWidth;

	private Integer maxWidth;

	private Integer minHeight;

	private Integer maxHeight;

	private String dimensionCompulsary;


	public Integer getDocId() {
		return docId;
	}


	public void setDocId(Integer docId) {
		this.docId = docId;
	}


	public String getTrxnNo() {
		return trxnNo;
	}


	public void setTrxnNo(String trxnNo) {
		this.trxnNo = trxnNo;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public void setCompulsary(String compulsary) {
		this.compulsary = compulsary;
	}


	public String getCompulsary() {
		return compulsary;
	}


	public String getDocDescEn() {
		return docDescEn;
	}


	public void setDocDescEn(String docDescEn) {
		this.docDescEn = docDescEn;
	}


	public String getDocDescMy() {
		return docDescMy;
	}


	public void setDocDescMy(String docDescMy) {
		this.docDescMy = docDescMy;
	}


	public int getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}


	@JsonIgnore
	public boolean is_DimensionCompulsary() {
		if ("Y".equalsIgnoreCase(dimensionCompulsary) || "true".equalsIgnoreCase(dimensionCompulsary)) {
			return true;
		}
		return false;
	}


	public void setDimensionCompulsary(String dimensionCompulsary) {
		this.dimensionCompulsary = dimensionCompulsary;
	}


	public String getDimensionCompulsary() {
		return dimensionCompulsary;
	}


	public Integer getMinWidth() {
		return minWidth;
	}


	public Integer getMaxWidth() {
		return maxWidth;
	}


	public Integer getMinHeight() {
		return minHeight;
	}


	public Integer getMaxHeight() {
		return maxHeight;
	}


	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}


	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}


	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}


	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

}