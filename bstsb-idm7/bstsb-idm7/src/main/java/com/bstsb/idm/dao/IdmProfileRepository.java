/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.model.IdmProfile;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Repository
@RepositoryDefinition(domainClass = IdmProfile.class, idClass = Integer.class)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_REPOSITORY)
public interface IdmProfileRepository extends GenericRepository<IdmProfile> {

	@Query("select u from IdmProfile u where u.userId = :userId ")
	public IdmProfile findUserProfileByUserId(@Param("userId") String userId);


	@Query("select u from IdmProfile u ")
	public List<IdmProfile> findAllProfiles();


	@Query("select u from IdmProfile u where u.status='A' ")
	public List<IdmProfile> findAllProfilesActive();


	@Query("select u from IdmProfile u where u.userType = :userType")
	public List<IdmProfile> findAllProfilesByUserType(@Param("userType") String userType);


	@Query("select u from IdmProfile u where u.userId = :userId and u.userType = :userType")
	public IdmProfile findUserProfileByUserIdAndUserType(@Param("userId") String userId,
			@Param("userType") String userType);


	@Query("select u from IdmProfile u where u.userRoleGroupCode = :userRoleGroupCode")
	public List<IdmProfile> findAllProfilesByUserGrpCode(@Param("userRoleGroupCode") String userRoleGrpCode);


	@Modifying
	@Query("update IdmProfile u set u.syncFlag = :syncFlag where u.userId = :userId")
	public int updateSyncFlag(@Param("userId") String userId, @Param("syncFlag") Integer syncFlag);


	@Query("select count(u) from IdmProfile u where u.userType = :userType")
	public int totalRecords(@Param("userType") String userType);


	@Query("select u from IdmProfile u where u.profId = :profId ")
	public IdmProfile findUserProfileByProfId(@Param("profId") Integer profId);


	@Query("select u from IdmProfile u where u.profId = :profId and u.userType = :userType")
	public IdmProfile findUserProfileByProfIdAndUserType(@Param("profId") Integer profId,
			@Param("userType") String userType);


	@Query("select u from IdmProfile u where u.userType = :userType and userRoleGroupCode = :userRoleGroupCode and u.status = :status ")
	public List<IdmProfile> findProfileByUserTypeRoleGrp(@Param("userType") String userType,
			@Param("userRoleGroupCode") String userRoleGroupCode, @Param("status") String status);


	@Query("select count(u) from IdmProfile u where u.profId = :profId")
	public int findMaxCount(@Param("profId") Integer profId);


	@Query("select count(u) from IdmProfile u where u.profId = :profId and u.branchId = :branchId")
	public int findMaxCount(@Param("profId") Integer profId, @Param("branchId") Integer branchId);


	@Query("select count(u) from IdmProfile u where u.userRoleGroupCode = :userRoleGroupCode")
	public int findMaxCountByUserGroup(@Param("userRoleGroupCode") String userRoleGroupCode);

}