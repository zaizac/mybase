/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.WfwAsgnTranDao;
import com.wfw.model.WfwAsgnTran;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_ASGN_TRAN_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_ASGN_TRAN_DTO)
public class WfwAsgnTranDtoImp extends AbstractService<WfwAsgnTran> implements WfwAsgnTranDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwAsgnTranDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_ASGN_TRAN_DAO)
	private WfwAsgnTranDao wfwAsgnTranDao;


	@Override
	public List<WfwAsgnTran> findWfAsgnTranByModuleId(String moduleId) {
		return wfwAsgnTranDao.findWfWfAsgnTranByModuleId(moduleId);
	}


	@Override
	public List<WfwAsgnTran> findAllWfAsgnTran() {
		return wfwAsgnTranDao.findAll();
	}


	@Override
	public WfwAsgnTran findAsgnTranByTranId(String tranId) {
		return wfwAsgnTranDao.findWfWfAsgnTranByTranId(tranId);
	}


	@Override
	public WfwAsgnTran findWfAsgnTranByTypeId(String typeId) {
		return wfwAsgnTranDao.findWfWfAsgnTranByTypeId(typeId);
	}


	@Override
	public GenericDao<WfwAsgnTran> primaryDao() {
		return wfwAsgnTranDao;
	}


	@Override
	public boolean addWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		try {
			create(wfwAsgnTran);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfAsgnTran: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		try {
			update(wfwAsgnTran);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfAsgnTran: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfAsgnTran(WfwAsgnTran wfwAsgnTran) {
		try {
			this.delete(wfwAsgnTran);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfAsgnTran: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfAsgnTran(Integer transId) {
		try {
			this.delete(transId);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfAsgnTran: {}", e.getMessage());
			return false;
		}
	}

}
