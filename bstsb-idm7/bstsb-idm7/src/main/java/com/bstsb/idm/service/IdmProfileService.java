/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.List;

import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmProfileRepository;
import com.bstsb.idm.dao.UserProfileCustomDao;
import com.bstsb.idm.ldap.IdmUserDao;
import com.bstsb.idm.ldap.IdmUserLdap;
import com.bstsb.idm.model.IdmProfile;
import com.bstsb.idm.sdk.model.UserProfile;
import com.bstsb.util.BaseUtil;
import com.bstsb.util.pagination.DataTableRequest;
import com.bstsb.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional
@Service(QualifierConstants.IDM_PROFILE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_SERVICE)
public class IdmProfileService extends AbstractService<IdmProfile> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmProfileService.class);

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	@Qualifier(QualifierConstants.IDM_PROFILE_REPOSITORY)
	private IdmProfileRepository idmProfileDao;

	@Autowired
	@Qualifier(QualifierConstants.IDM_PROFILE_CUSTOM_REPOSITORY)
	private UserProfileCustomDao userProfileCustomDao;


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmProfile findProfileByUserId(String userId) {
		return idmProfileDao.findUserProfileByUserId(userId);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmProfile findUserProfileByUserIdAndUserType(String userId, String userType) {
		return idmProfileDao.findUserProfileByUserIdAndUserType(userId, userType);
	}


	@Override
	public GenericRepository<IdmProfile> primaryDao() {
		return idmProfileDao;
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createUser(IdmUserLdap ldap, IdmProfile up) {
		try {
			if(!BaseUtil.isObjNull(ldap)) {
				idmUserDao.createEntry(ldap);
			}
			super.create(up);
		} catch (Exception e) {
			LOGGER.error("{}", e);
			return false;
		}

		return true;
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateUser(IdmUserLdap ldap, IdmProfile up, boolean isUpdateLdap) {
		try {
			if (isUpdateLdap) {
				idmUserDao.updateMail(ldap);
			}
			super.update(up);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateUserForForgotPwd(IdmUserLdap ldap, IdmProfile up) {
		try {
			idmUserDao.changePassword(ldap, false);
			super.update(up);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllProfiles() {
		return idmProfileDao.findAllProfiles();
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllProfilesActive() {
		return idmProfileDao.findAllProfilesActive();
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllProfilesByUserType(String userType) {
		return idmProfileDao.findAllProfilesByUserType(userType);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> searchProfile(UserProfile userProfile) {
		return userProfileCustomDao.searchUserProfile(userProfile);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> searchUserProfile(UserProfile userProfile) {
		return userProfileCustomDao.search(userProfile);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> userProfileListByUsernameList(String usernameList) {
		return userProfileCustomDao.getUserProfileListByUserNameList(usernameList);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllUsersByRoleCodes(String userRoles) {
		return userProfileCustomDao.findAllUsersByRoleCodes(userRoles);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmProfile findProfileByProfId(Integer profId) {
		return idmProfileDao.findUserProfileByProfId(profId);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmProfile findUserProfileByProfIdAndUserType(Integer profId, String userType) {
		return idmProfileDao.findUserProfileByProfIdAndUserType(profId, userType);
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateBulkUsers(List<IdmProfile> up) {
		try {
			idmProfileDao.save(up);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllUsersByIdmRoleroleCode(String roleCode, String userId) {
		return userProfileCustomDao.findRoleMemberByIdmRoleroleCode(roleCode, userId);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmProfile> findAllUsersByIdmRoleroleCode(String roleCode, String userId,
			String... userRoleGroupCodes) {
		return userProfileCustomDao.findRoleMemberByIdmRoleroleCode(roleCode, userId, userRoleGroupCodes);
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateSyncFlag(String userId, Integer syncFlag) {
		return idmProfileDao.updateSyncFlag(userId, syncFlag);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public DataTableResults<IdmProfile> searchUserProfile(UserProfile userProfile,
			DataTableRequest<IdmProfile> dataTableInRQ) {
		return userProfileCustomDao.searchByPagination(userProfile, dataTableInRQ);
	}


	public List<IdmProfile> findProfileByUserTypeRoleGrp(String userType, String userRoleGroupCode, String status) {
		return idmProfileDao.findProfileByUserTypeRoleGrp(userType, userRoleGroupCode, status);
	}


	public int findMaxCountByUserGroup(String userRoleGroupCode) {
		return idmProfileDao.findMaxCountByUserGroup(userRoleGroupCode);
	}


	public int findMaxCount(Integer profId) {
		return idmProfileDao.findMaxCount(profId);
	}


	public int findMaxCount(Integer profId, Integer branchId) {
		return idmProfileDao.findMaxCount(profId, branchId);
	}

}