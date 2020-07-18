/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmMenu;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmMenu.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_MENU_REPOSITORY)
public interface IdmMenuRepository extends GenericRepository<IdmMenu> {

	@Query("select u from IdmMenu u where u.menuCode = :menuCode ")
	public IdmMenu findMenuByMenuCode(@Param("menuCode") String menuCode);


	@Query("select u from IdmMenu u where u.menuParentCode = :menuParentCode ")
	public List<IdmMenu> findAllMenuByPerentCode(@Param("menuParentCode") String menuParentCode);


	@Query("select u from IdmMenu u where u.menuLevel = :menuLevel ")
	public List<IdmMenu> findMenuByMenuLevel(@Param("menuLevel") Integer menuLevel);

}