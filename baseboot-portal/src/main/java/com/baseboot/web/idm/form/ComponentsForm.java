/**
 * 
 */
package com.baseboot.web.idm.form;


import java.util.Date;
import java.util.List;

import com.baseboot.web.dto.FileUpload;


/**
 * @author Mary Jane Buenaventura
 * @since 10/07/2018
 */
public class ComponentsForm {

	List<FileUpload> fileUploads;

	String select;

	String[] selectMultiple;

	String[] selectMultipleFilter;

	String selectFilter;

	Date dt;

	Date dtRange;

	Date dtRangeStart;

	Date dtRangeEnd;

	String time;

	String captcha;
	
	String otpCode;


	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}


	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}


	public String[] getSelectMultiple() {
		return selectMultiple;
	}


	public void setSelectMultiple(String[] selectMultiple) {
		this.selectMultiple = selectMultiple;
	}


	public String[] getSelectMultipleFilter() {
		return selectMultipleFilter;
	}


	public void setSelectMultipleFilter(String[] selectMultipleFilter) {
		this.selectMultipleFilter = selectMultipleFilter;
	}


	public String getSelect() {
		return select;
	}


	public void setSelect(String select) {
		this.select = select;
	}


	public String getSelectFilter() {
		return selectFilter;
	}


	public void setSelectFilter(String selectFilter) {
		this.selectFilter = selectFilter;
	}


	public Date getDt() {
		return dt;
	}


	public void setDt(Date dt) {
		this.dt = dt;
	}


	public Date getDtRange() {
		return dtRange;
	}


	public void setDtRange(Date dtRange) {
		this.dtRange = dtRange;
	}


	public Date getDtRangeStart() {
		return dtRangeStart;
	}


	public void setDtRangeStart(Date dtRangeStart) {
		this.dtRangeStart = dtRangeStart;
	}


	public Date getDtRangeEnd() {
		return dtRangeEnd;
	}


	public void setDtRangeEnd(Date dtRangeEnd) {
		this.dtRangeEnd = dtRangeEnd;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getCaptcha() {
		return captcha;
	}


	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	
	public String getOtpCode() {
		return otpCode;
	}
	
	
	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

}
