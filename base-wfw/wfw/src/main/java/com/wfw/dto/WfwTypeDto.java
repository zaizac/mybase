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
import com.wfw.model.WfwType;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_TYPE_DTO)
@EnableTransactionManagement
public interface WfwTypeDto {

	public List<WfwType> findByWfwTypeByDescription(String description);


	public List<WfwType> findAllWfType();


	public WfwType findByWfTypeId(String typeId);


	public boolean addWfType(WfwType wfwType);


	public boolean editWfType(WfwType wfwType);


	public boolean deleteWfType(WfwType wfwType);


	public boolean deleteWfType(Integer typeId);

}
