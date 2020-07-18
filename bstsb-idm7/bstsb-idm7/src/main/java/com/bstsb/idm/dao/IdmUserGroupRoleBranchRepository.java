/**
 * Copyright 2019. Bestinet Sdn Bhd
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
import com.bstsb.idm.model.IdmUserGroupRoleBranch;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserGroupRoleBranch.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLE_BRANCH_DAO)
public interface IdmUserGroupRoleBranchRepository extends GenericRepository<IdmUserGroupRoleBranch> {

	@Query("select u from IdmUserGroupRoleBranch u where u.branchCode = :branchCode ")
	public IdmUserGroupRoleBranch findByBranchCode(@Param("branchCode") String branchCode);


	@Query("select u from IdmUserGroupRoleBranch u where u.userGroupRoleCode = :userGroupRoleCode ")
	public List<IdmUserGroupRoleBranch> findByUserGroupRoleCode(@Param("userGroupRoleCode") String userGroupRoleCode);

}