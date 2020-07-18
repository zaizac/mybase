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
import com.wfw.model.WfwLevel;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_LEVEL_DTO)
@EnableTransactionManagement
public interface WfwLevelDto {

	public WfwLevel findLevelByLevelId(String levelId);


	public List<WfwLevel> findAllWfLevel();


	public List<WfwLevel> findWfLevelByDescription(String description);


	public List<WfwLevel> findWfLevelByTypeId(String typeId);


	public boolean addWfLevel(WfwLevel wfwLevel);


	public boolean editWfLevel(WfwLevel wfwLevel);


	public boolean deleteWfLevel(WfwLevel wfwLevel);


	public boolean deleteWfLevel(Integer id);

}
