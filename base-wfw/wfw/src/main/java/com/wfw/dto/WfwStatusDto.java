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
import com.wfw.model.WfwStatus;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_STATUS_DTO)
@EnableTransactionManagement
public interface WfwStatusDto {

	public WfwStatus findStatusByStatusId(String statusId);


	public WfwStatus findFirstStatusByLevelId(String levelId);


	public List<WfwStatus> findAllWfStatus();


	public List<WfwStatus> findWfStatusByDescription(String description);


	public List<WfwStatus> findWfStatusByLevelId(String levelId);


	public List<WfwStatus> findAllWfStatusByLevelId(String levelId);


	public List<WfwStatus> findWfStatusByLevelIdDisplayYes(String levelId);


	public WfwStatus findWfStatusByLevelIdAppStatus(String levelId, String appStatus);


	public boolean addWfStatus(WfwStatus wfwStatus);


	public boolean editWfStatus(WfwStatus wfwStatus);


	public boolean deleteWfStatus(WfwStatus wfwStatus);


	public boolean deleteWfStatusById(Integer id);

}
