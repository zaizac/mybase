/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baseboot.notify.core.AbstractService;
import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.dao.NotAnnouncementRepository;
import com.baseboot.notify.model.NotAnnouncement;
import com.baseboot.notify.model.NotConfig;
import com.baseboot.notify.util.QualifierConstants;


@Service(QualifierConstants.NOT_ANNOUNCEMENT_SVC)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_ANNOUNCEMENT_SVC)
@Transactional
public class NotAnnouncementService extends AbstractService<NotAnnouncement, Integer> {

	@Autowired
	private NotAnnouncementRepository notAnnouncementDao;


	@Override
	public GenericRepository<NotAnnouncement> primaryDao() {
		return notAnnouncementDao;
	}
	
	@Transactional(readOnly=true, rollbackFor=Exception.class)
	public List<NotAnnouncement> findAllProfiles(){
		return this.notAnnouncementDao.findAllProfiles();
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public NotAnnouncement findByAnncId(Integer anncId) {
		return notAnnouncementDao.findByAnncId(anncId);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<NotAnnouncement> updateAll(List<NotAnnouncement> notAnnounce) {
		for (NotAnnouncement notAnnouncement : notAnnounce) {
			notAnnouncementDao.saveAndFlush(notAnnouncement);
		}
		return notAnnounce;
	}
	
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public int deleteAnnouncementByAnncId(Integer anncId) {
		return notAnnouncementDao.deleteAnnouncementByAnncId(anncId);
	}

}