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
import com.baseboot.idm.model.IdmRoleMenu;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmRoleMenu.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMROLEMENU_REPOSITORY)
public interface IdmRoleMenuRepository extends GenericRepository<IdmRoleMenu> {

	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode = :roleCode ")
	public List<IdmRoleMenu> findRoleMenuByIdmRoleRoleCode(@Param("roleCode") String roleCode);


	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode = :roleCode and u.idmMenu.menuCode = :menuCode ")
	public List<IdmRoleMenu> findRoleMenuByIdmRoleRoleCodeAndIdmMenuMenuCode(@Param("roleCode") String roleCode,
			@Param("menuCode") String menuCode);


	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode in :roleCode order by u.idmMenu.menuLevel, u.idmMenu.menuSequence")
	public List<IdmRoleMenu> findRoleMenuByRoleList(@Param("roleCode") List<String> roleCode);


	@Query("select max(u.idmMenu.menuLevel) from IdmRoleMenu u where u.idmRole.roleCode in :roleCode")
	public Integer maxMenuLevel(@Param("roleCode") List<String> roleCode);

}