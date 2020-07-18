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
import com.idm.model.IdmMenu;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmMenu.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_MENU_REPOSITORY)
public interface IdmMenuRepository extends GenericRepository<IdmMenu> {

	public IdmMenu findByMenuCode(String menuCode);

	public List<IdmMenu> findByMenuParentCode(String menuParentCode);
	

	public List<IdmMenu> findByMenuLevel(Integer menuLevel);
	
//	@Query("select max(u.menuSequence) from IdmMenu u where u.menuParentCode = :menuParentCode and u.menuLevel = :menuLevel ")
//	public IdmMenu findMenuSeqByMenuParentCode(@Param("menuParentCode") String menuParentCode,
//			@Param("menuLevel") String menuLevel);
	
	@Query("select max(u.menuSequence) +1 from IdmMenu u where u.menuParentCode = :menuParentCode ")
	Integer findByMaxMenuSequence(@Param("menuParentCode") String menuParentCode);

}