/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.dto;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.AbstractService;
import com.wfw.core.GenericDao;
import com.wfw.dao.JpqlQueryFactory;
import com.wfw.dao.WfwInboxHistDao;
import com.wfw.model.WfwInboxHist;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_INBOX_HIST_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_HIST_DTO)
public class WfwInboxHistDtoImp extends AbstractService<WfwInboxHist> implements WfwInboxHistDto {

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_HIST_DAO)
	private WfwInboxHistDao wfwInboxHistDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_JPQLQF_DAO)
	private JpqlQueryFactory jpqlQueryFactoryDao;


	@Override
	@Transactional(readOnly = true)
	public List<WfwInboxHist> findWfInboxHistByRefNo(String refNo) {
		return wfwInboxHistDao.findWfInboxHistByRefNo(refNo);
	}


	@Override
	@Transactional(readOnly = true)
	public List<WfwInboxHist> findAllWfInboxHist() {
		return wfwInboxHistDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public List<WfwInboxHist> findWfInboxHistByTaskId(String taskId) {
		return wfwInboxHistDao.findWfInboxHistByTaskId(taskId);
	}


	@Override
	@Transactional(readOnly = true)
	public WfwInboxHist findWfInboxHistByHistoryId(String historyId) {
		return wfwInboxHistDao.findWfInboxHistByHistId(historyId);
	}


	@Override
	public GenericDao<WfwInboxHist> primaryDao() {
		return wfwInboxHistDao;
	}


	@Override
	public void addWfInboxHist(WfwInboxHist wfwInboxHist) {
		create(wfwInboxHist);
	}


	@Override
	public void editWfInboxHist(WfwInboxHist wfwInboxHist) {
		update(wfwInboxHist);
	}


	@Override
	public void deleteWfInboxHist(WfwInboxHist wfwInboxHist) {
		this.delete(wfwInboxHist);
	}


	@Override
	public void deleteWfInboxHist(Integer taskId) {
		this.delete(taskId);
	}


	@Override
	public List<WfwInboxHist> findHistoryByTaskId(String taskId) {
		List<WfwInboxHist> list = new ArrayList<>();
		List<Object[]> histories = jpqlQueryFactoryDao.findHistoryByTaskId(taskId);
		int count = 0;
		for (Object[] objects : histories) {
			WfwInboxHist prHist = new WfwInboxHist();
			prHist.setHistId((String) objects[count++]);
			prHist.setTaskId((String) objects[count++]);
			prHist.setRefNo((String) objects[count++]);
			prHist.setLevelDescription((String) objects[count++]);
			prHist.setStatusDescription((String) objects[count++]);
			prHist.setApplStsId((String) objects[count++]);
			prHist.setCreateId((String) objects[count++]);
			prHist.setOfficerName((String) objects[count++]);
			prHist.setCreateDt((Timestamp) objects[count++]);
			prHist.setApplRemark((String) objects[count++]);
			prHist.setApplStsCode((String) objects[count]);
			list.add(prHist);
			count = 0;
		}
		return list;
	}

}
