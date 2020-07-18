/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wfw.constant.QualifierConstants;
import com.wfw.model.WfwInbox;
import com.wfw.model.WfwInboxMstr;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_DTO)
public interface WfwInboxDto {

	WfwInbox findWfInboxByTaskId(String taskId);


	WfwInbox findWfInboxByTaskIdAndOfficerId(String taskId, String officerId);
	
	
	WfwInbox findWfInboxByRefNoAndOfficerId(String refNo, String officerId);


	List<WfwInbox> findAllWfInbox();


	List<WfwInbox> findWfInboxByRefNo(String refNo);


	List<WfwInbox> searchWfInbox(WfwInbox wfwInbox);


	boolean addWfInbox(WfwInbox wfwInbox);


	boolean editWfInbox(WfwInbox wfwInbox);


	boolean deleteWfInbox(WfwInbox wfwInbox);


	boolean deleteWfInbox(String taskId);


	boolean deleteWfInboxByInboxId(String inboxId);


	List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String moduleId, String refNo);


	String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId);

}
