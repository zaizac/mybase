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
import com.wfw.model.WfwAsgnTran;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_TRAN_DTO)
@EnableTransactionManagement
public interface WfwAsgnTranDto {

	public WfwAsgnTran findAsgnTranByTranId(String tranId);


	public List<WfwAsgnTran> findAllWfAsgnTran();


	public WfwAsgnTran findWfAsgnTranByTypeId(String typeId);


	public List<WfwAsgnTran> findWfAsgnTranByModuleId(String moduleId);


	public boolean addWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	public boolean editWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	public boolean deleteWfAsgnTran(WfwAsgnTran wfwAsgnTran);


	public boolean deleteWfAsgnTran(Integer tranId);

}
