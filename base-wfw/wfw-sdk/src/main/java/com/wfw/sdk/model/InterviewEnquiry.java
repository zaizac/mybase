package com.wfw.sdk.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nigel
 * @since 20170202
 */

public class InterviewEnquiry implements Serializable{

	private static final long serialVersionUID = -9007188289490234501L;

	private Date interviewDateFrom;
	
	private Date interviewDateTo;
	
	private Date interviewDate;
	
	private long noOfEmployer;
	
	public InterviewEnquiry() {
		super();
	}
	
	public InterviewEnquiry(Date interviewDate, long noOfEmployer) {
		this.interviewDate = interviewDate;
		this.noOfEmployer = noOfEmployer;	
	}

	public Date getInterviewDateFrom() {
		return interviewDateFrom;
	}

	public void setInterviewDateFrom(Date interviewDateFrom) {
		this.interviewDateFrom = interviewDateFrom;
	}

	public Date getInterviewDateTo() {
		return interviewDateTo;
	}

	public void setInterviewDateTo(Date interviewDateTo) {
		this.interviewDateTo = interviewDateTo;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public long getNoOfEmployer() {
		return noOfEmployer;
	}

	public void setNoOfEmployer(long noOfEmployer) {
		this.noOfEmployer = noOfEmployer;
	}

}
