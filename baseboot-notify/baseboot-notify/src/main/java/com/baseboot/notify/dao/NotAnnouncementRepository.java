/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.notify.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.notify.core.GenericRepository;
import com.baseboot.notify.model.NotAnnouncement;
import com.baseboot.notify.util.QualifierConstants;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;


@Repository
@RepositoryDefinition(domainClass = NotAnnouncement.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.NOT_ANNOUNCEMENT_DAO)
public interface NotAnnouncementRepository extends GenericRepository<NotAnnouncement> {

	
	@Query("select u from NotAnnouncement u ")
	public List<NotAnnouncement> findAllProfiles();
	
	@Query("select u from NotAnnouncement u where u.anncId= :anncId ")
	public NotAnnouncement findByAnncId(@Param("anncId") Integer anncId);
	
	@Transactional
	@Modifying
	@Query("delete from NotAnnouncement u where u.anncId = :anncId")
	public int deleteAnnouncementByAnncId(@Param("anncId") Integer anncId);

}