package com.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.core.GenericRepository;
import com.idm.model.IdmProfile;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Repository
@RepositoryDefinition(domainClass = IdmProfile.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_REPOSITORY)
public interface IdmProfileRepository extends GenericRepository<IdmProfile> {

	IdmProfile findByUserId(String userId);


	IdmProfile findByUserIdAndUserTypeUserTypeCode(String userId, String userType);


	IdmProfile findByProfId(Integer profId);


	IdmProfile findByProfIdAndUserTypeUserTypeCode(Integer profId, String userType);
	
	
	IdmProfile findByActivationCode(String activationCode);
	
	
	IdmProfile findByUserIdAndActivationCode(String userId, String activationCode);


	@Query("select u from IdmProfile u where u.status='A' ")
	List<IdmProfile> findAllProfilesActive();


	@Query("select u from IdmProfile u where u.userType.userTypeCode = :userTypeCode and u.userRoleGroup.userGroupCode = :userRoleGroupCode and u.status = :status ")
	List<IdmProfile> findProfileByUserTypeRoleGrp(@Param("userTypeCode") String userTypeCode,
			@Param("userRoleGroupCode") String userRoleGroupCode, @Param("status") String status);


	List<IdmProfile> findByStatusAndUserTypeUserTypeCodeAndUserRoleGroupUserGroupCode(String status, String userType,
			String userRoleGroup);


	@Query("select count(u) from IdmProfile u where u.userType.userTypeCode = :userTypeCode")
	int totalRecords(@Param("userTypeCode") String userTypeCode);


	@Query("select count(u) from IdmProfile u where u.userId = :userId and u.profId = :profId")
	int findMaxCount(@Param("userId") String currUserId, @Param("profId") Integer profId);


	@Modifying
	@Query("update IdmProfile u set u.syncFlag = :syncFlag where u.userId = :userId")
	int updateSyncFlag(@Param("userId") String userId, @Param("syncFlag") Integer syncFlag);


	@Query("select count(u) from IdmProfile u where u.userId = :userId and u.profId = :profId and u.branchId = :branchId")
	int findMaxCount(@Param("userId") String currUserId, @Param("profId") Integer profId,
			@Param("branchId") Integer branchId);
}