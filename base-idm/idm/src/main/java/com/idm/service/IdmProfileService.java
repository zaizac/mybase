package com.idm.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idm.constants.IdmConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmProfileQf;
import com.idm.dao.IdmProfileRepository;
import com.idm.dao.UserProfileCustomDao;
import com.idm.ldap.IdmUserDao;
import com.idm.ldap.IdmUserLdap;
import com.idm.model.IdmConfig;
import com.idm.model.IdmProfile;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.UserProfile;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.JsonUtil;
import com.util.UidGenerator;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;
import com.util.pagination.DataTableResults;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_PROFILE_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_PROFILE_SERVICE)
public class IdmProfileService extends AbstractService<IdmProfile> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmProfileService.class);

	@Autowired
	private IdmUserDao idmUserDao;

	@Autowired
	private IdmProfileRepository idmProfileDao;

	@Autowired
	private IdmProfileQf idmProfileQf;

	@Autowired
	@Qualifier(QualifierConstants.IDM_PROFILE_CUSTOM_REPOSITORY)
	private UserProfileCustomDao userProfileCustomDao;
	
	@Autowired
	IdmConfigService idmConfigSvc;



	@Override
	public GenericRepository<IdmProfile> primaryDao() {
		return idmProfileDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmProfileQf.generateCriteria(cb, from, criteria);
	}


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserProfile> searchByPagination(UserProfile t, DataTableRequest<?> dataTableInRQ) {
		try {
			List<IdmProfile> result = idmProfileQf.searchByPagination(t, dataTableInRQ);
			return JsonUtil.transferToList(result, UserProfile.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdmException(e.getMessage());
		}
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmProfile findByUserId(String userId) {
		return idmProfileDao.findByUserId(userId);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmProfile findUserProfileByUserIdAndUserType(String userId, String userType) {
		return idmProfileDao.findByUserIdAndUserTypeUserTypeCode(userId, userType);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> searchUserProfile(UserProfile userProfile, boolean embededRole, boolean embededMenu) {
		return idmProfileQf.searchUserProfile(userProfile, embededRole, embededMenu);
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createUser(IdmUserLdap ldap, IdmProfile up) {
		boolean ldapCreated = false;
		try {
			ldapCreated = idmUserDao.createEntry(ldap);
			if (ldapCreated) {
				super.create(up);
				ldapCreated = true;
			}
		} catch (IdmException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("{}", e);
			ldapCreated = false;
		}

		return ldapCreated;
	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createUser(IdmProfile up) {
		boolean userCreated = false;
		try {
			super.create(up);
			userCreated = true;
		} catch (IdmException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("{}", e);
			userCreated = false;
		}

		return userCreated;
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


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> findAllProfilesActive() {
		return idmProfileDao.findAllProfilesActive();
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> searchProfile(UserProfile userProfile) {
		return userProfileCustomDao.searchUserProfile(userProfile);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> searchUserProfile(UserProfile userProfile) {
		return userProfileCustomDao.search(userProfile);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> userProfileListByUsernameList(String usernameList) {
		return userProfileCustomDao.getUserProfileListByUserNameList(usernameList);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> findAllUsersByRoleCodes(String userRoles) {
		return userProfileCustomDao.findAllUsersByRoleCodes(userRoles);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmProfile findProfileByProfId(Integer profId) {
		return idmProfileDao.findByProfId(profId);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmProfile findUserProfileByProfIdAndUserType(Integer profId, String userType) {
		return idmProfileDao.findByProfIdAndUserTypeUserTypeCode(profId, userType);
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


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> findAllUsersByIdmRoleroleCode(String roleCode, String userId) {
		return userProfileCustomDao.findRoleMemberByIdmRoleroleCode(roleCode, userId);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmProfile> findAllUsersByIdmRoleroleCode(String roleCode, String userId,
			String... userRoleGroupCodes) {
		return userProfileCustomDao.findRoleMemberByIdmRoleroleCode(roleCode, userId, userRoleGroupCodes);
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateSyncFlag(String userId, Integer syncFlag) {
		return idmProfileDao.updateSyncFlag(userId, syncFlag);
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public DataTableResults<IdmProfile> searchUserProfile(UserProfile userProfile,
			DataTableRequest<IdmProfile> dataTableInRQ) {
		return userProfileCustomDao.searchByPagination(userProfile, dataTableInRQ);
	}


	public List<IdmProfile> findByStatusAndUserTypeUserTypeCodeAndUserRoleGroupUserGroupCode(String userType,
			String userRoleGroupCode, String status) {
		return idmProfileDao.findByStatusAndUserTypeUserTypeCodeAndUserRoleGroupUserGroupCode(userType,
				userRoleGroupCode, status);
	}


	public int findMaxCount(String currUserId, Integer profId) {
		return idmProfileDao.findMaxCount(currUserId, profId);
	}


	public int findMaxCount(String currUserId, Integer profId, Integer branchId) {
		return idmProfileDao.findMaxCount(currUserId, profId, branchId);
	}
	

	/**
	 * Generate Activation Code in IDM PROFILE
	 * 
	 * @param idmProfile
	 * @return IdmProfile
	 * @throws IOException
	 */
	public IdmProfile generateActivationCode(IdmProfile idmProfile) {
		if (!BaseUtil.isObjNull(idmProfile)) {
			boolean isNumeric = true;
			int length = 6;
			
			// retrieve ACTIVATE_CODE_ISNUMERIC from IDM_CONFIG
			IdmConfig idmConfig = idmConfigSvc.findByConfigCode(IdmConfigConstants.ACTIVATE_CODE_ISNUMERIC);
			if (!BaseUtil.isObjNull(idmConfig)) {
				isNumeric = Boolean.parseBoolean(idmConfig.getConfigVal());
			}
			// retrieve ACTIVATE_CODE_LENGTH from IDM_CONFIG
			idmConfig = idmConfigSvc.findByConfigCode(IdmConfigConstants.ACTIVATE_CODE_LENGTH);
			if (!BaseUtil.isObjNull(idmConfig)) {
				length = Integer.parseInt(idmConfig.getConfigVal());
			}

			String activationCode = isNumeric 	? UidGenerator.generateRandomNoInStr()
													: RandomStringUtils.random(length, true, true) ;
			idmProfile.setActivationCode(activationCode);
			// update database
			idmProfileDao.save(idmProfile);
		}
		return idmProfile;
	}
	
	/**
	 * Verify activation code based on User Id
	 * @param userId
	 * @param activationCode
	 * @return
	 * @throws IOException
	 */
	public boolean verifyActivationCode(String userId, String activationCode) throws IOException {
		IdmProfile idmProfile = idmProfileDao.findByUserIdAndActivationCode(userId, activationCode);
		return !BaseUtil.isObjNull(idmProfile);
	}
	
	
	/**
	 * method for report Total Registered User By Day
	 */
	public List<UserProfile> getCountRegisteredUserByDay() {
		IdmConfig idmConfig = idmConfigSvc.findByConfigCode(IdmConfigConstants.REGISTER_DATE);
		String date = null ; 
		if (!BaseUtil.isObjNull(idmConfig)) {
			date = idmConfig.getConfigVal();
		}
		List<UserProfile> list = new ArrayList<>();
		for (Object[] obj : idmProfileQf.getCountRegisteredUserByDay(date)) {
			if (!BaseUtil.isObjNull(obj[0])) {
				UserProfile idm = new UserProfile();
				idm.setCountUser(Integer.parseInt(obj[0].toString()));
				idm.setCreateDt(DateUtil.convertDate2SqlTimeStamp(DateUtil.convertStrDateToDate(obj[1].toString())));
				list.add(idm);
			}
		}
		return list;
	}
}