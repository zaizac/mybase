package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmRoleMenu;
import com.idm.model.IdmUserGroupRole;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmRoleMenu.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLEMENU_REPOSITORY)
public interface IdmRoleMenuRepository extends GenericRepository<IdmRoleMenu> {

	public List<IdmRoleMenu> findByIdmRoleRoleCode(String roleCode);
	
	public List<IdmRoleMenu> findByIdmMenuMenuCode(String menuCode);
	
	public List<IdmRoleMenu> findByIdmPortalTypePortalTypeCode(String portalTypeCode);


	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode = :roleCode and u.idmMenu.menuCode = :menuCode ")
	public List<IdmRoleMenu> findRoleMenuByIdmRoleRoleCodeAndIdmMenuMenuCode(@Param("roleCode") String roleCode,
			@Param("menuCode") String menuCode);


	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode in :roleCode order by u.idmMenu.menuLevel, u.idmMenu.menuSequence")
	public List<IdmRoleMenu> findRoleMenuByRoleList(@Param("roleCode") List<String> roleCode);

	@Query("select u from IdmRoleMenu u where u.idmRole.roleCode in :roleCode and upper(u.idmPortalType.portalTypeCode) = upper(:portalTypeCode) order by u.idmMenu.menuLevel, u.idmMenu.menuSequence")
	public List<IdmRoleMenu> findRoleMenuByPortalTypeCodeAndRoleList(@Param("portalTypeCode") String portalTypeCode, @Param("roleCode") List<String> roleCode);

	@Query("select max(u.idmMenu.menuLevel) from IdmRoleMenu u where u.idmRole.roleCode in :roleCode")
	public Integer maxMenuLevel(@Param("roleCode") List<String> roleCode);
	
	@Query("select u from IdmRoleMenu u inner join fetch u.idmRole ut inner join fetch u.idmMenu r where u.roleMenuId = :id")
	public IdmRoleMenu findByRoleMenuId(@Param("id") Integer id);

}