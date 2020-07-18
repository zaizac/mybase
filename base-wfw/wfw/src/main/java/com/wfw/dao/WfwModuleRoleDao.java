package com.wfw.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wfw.constant.QualifierConstants;
import com.wfw.core.GenericDao;
import com.wfw.model.WfwModuleRole;


/**
 * @author mary.jane
 */
@Repository
@Scope(QualifierConstants.BEAN_SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.WF_MODULE_ROLE_DAO)
@RepositoryDefinition(domainClass = WfwModuleRole.class, idClass = Integer.class)
public interface WfwModuleRoleDao extends GenericDao<WfwModuleRole> {

	@Query("select distinct u.moduleId from WfwModuleRole u where u.roleCode in (:roles)")
	List<String> findModuleIdByRoles(@Param("roles") List<String> roles);

}
