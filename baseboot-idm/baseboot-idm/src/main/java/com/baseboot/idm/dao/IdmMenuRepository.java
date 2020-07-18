/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.model.IdmMenu;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmMenu.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMMENU_REPOSITORY)
public interface IdmMenuRepository extends GenericRepository<IdmMenu> {

	@Query("select u from IdmMenu u where u.menuCode = :menuCode ")
	public IdmMenu findMenuByMenuCode(@Param("menuCode") String menuCode);


	@Query("select u from IdmMenu u where u.menuParentCode = :menuParentCode ")
	public List<IdmMenu> findAllMenuByPerentCode(@Param("menuParentCode") String menuParentCode);


	@Query("select u from IdmMenu u where u.menuLevel = :menuLevel ")
	public List<IdmMenu> findMenuByMenuLevel(@Param("menuLevel") Integer menuLevel);

}