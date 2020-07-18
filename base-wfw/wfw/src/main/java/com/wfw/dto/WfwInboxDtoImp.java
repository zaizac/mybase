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
import com.wfw.dao.JpqlQueryFactory;
import com.wfw.dao.SearchDao;
import com.wfw.dao.WfwInboxDao;
import com.wfw.model.WfwInbox;
import com.wfw.model.WfwInboxMstr;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@Service(QualifierConstants.WF_INBOX_DTO)
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_INBOX_DTO)
public class WfwInboxDtoImp extends AbstractService<WfwInbox> implements WfwInboxDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(WfwInboxDtoImp.class);

	@Autowired
	@Qualifier(QualifierConstants.WF_INBOX_DAO)
	private WfwInboxDao wfwInboxDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_SEARCH_DAO)
	private SearchDao wfSearchDao;

	@Autowired
	@Qualifier(QualifierConstants.WF_JPQLQF_DAO)
	private JpqlQueryFactory jpqlQueryFactoryDao;


	@Override
	public List<WfwInbox> findWfInboxByRefNo(String refNo) {
		return wfwInboxDao.findWfInboxByRefNo(refNo);
	}


	@Override
	public List<WfwInbox> findAllWfInbox() {
		return wfwInboxDao.findAll();
	}


	@Override
	public WfwInbox findWfInboxByTaskId(String taskId) {
		return wfwInboxDao.findWfInboxByTaskId(taskId);
	}


	@Override
	public WfwInbox findWfInboxByTaskIdAndOfficerId(String taskId, String officerId) {
		return wfwInboxDao.findWfInboxByTaskIdAndOfficerId(taskId, officerId);
	}


	@Override
	public WfwInbox findWfInboxByRefNoAndOfficerId(String refNo, String officerId) {
		return wfwInboxDao.findWfInboxByRefNoAndOfficerId(refNo, officerId);
	}


	@Override
	public GenericDao<WfwInbox> primaryDao() {
		return wfwInboxDao;
	}


	@Override
	public boolean addWfInbox(WfwInbox wfwInbox) {
		try {
			create(wfwInbox);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: addWfInbox: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean editWfInbox(WfwInbox wfwInbox) {
		try {
			update(wfwInbox);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: editWfInbox: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfInbox(WfwInbox wfwInbox) {
		try {
			this.delete(wfwInbox);
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception: deleteWfInbox: {}", e.getMessage());
			return false;
		}
	}


	@Override
	public boolean deleteWfInbox(String taskId) {
		return jpqlQueryFactoryDao.deleteWfInboxByTaskId(taskId);
	}


	@Override
	public boolean deleteWfInboxByInboxId(String inboxId) {
		return jpqlQueryFactoryDao.deleteWfInboxByInboxId(inboxId);
	}


	@Override
	public List<WfwInbox> searchWfInbox(WfwInbox wfwInbox) {
		return wfwInboxDao.findAll(wfSearchDao.searchWfInBox(wfwInbox));
	}


	@Override
	public List<WfwInboxMstr> findInboxByOfficerIdModIdRefNo(String officerId, String moduleId, String refNo) {
		return jpqlQueryFactoryDao.findInboxByOfficerIdModIdRefNo(officerId, moduleId, refNo);
	}


	@Override
	public String findOfficerIdWithMinimumWorkLoad(String levelId, String currOfficerId) {
		return jpqlQueryFactoryDao.findOfficerIdWithMinimumWorkLoad(levelId, currOfficerId);
	}

}
