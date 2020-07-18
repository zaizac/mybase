package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmUserGroupBranch;


/**
 * @author mary.jane
 * @since Feb 7, 2019
 */
@Repository
@RepositoryDefinition(domainClass = IdmUserGroupBranch.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_BRANCH_DAO)
public interface IdmUserGroupBranchRepository extends GenericRepository<IdmUserGroupBranch> {

	IdmUserGroupBranch findByBranchCode(String branchCode);

	@Query("select u from IdmUserGroupBranch u inner join fetch u.userType p")
	public List<IdmUserGroupBranch> findAllIncludeUserType();
}