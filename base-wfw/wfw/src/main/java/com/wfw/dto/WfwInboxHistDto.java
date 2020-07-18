/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInboxHist;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_HIST_DTO)
@EnableTransactionManagement
public interface WfwInboxHistDto {

	public WfwInboxHist findWfInboxHistByHistoryId(String historyId);


	public List<WfwInboxHist> findWfInboxHistByTaskId(String taskId);


	public List<WfwInboxHist> findAllWfInboxHist();


	public List<WfwInboxHist> findWfInboxHistByRefNo(String refNo);


	public void addWfInboxHist(WfwInboxHist wfwInboxHist);


	public void editWfInboxHist(WfwInboxHist wfwInboxHist);


	public void deleteWfInboxHist(WfwInboxHist wfwInboxHist);


	public void deleteWfInboxHist(Integer taskId);


	public List<WfwInboxHist> findHistoryByTaskId(String taskId);

}
