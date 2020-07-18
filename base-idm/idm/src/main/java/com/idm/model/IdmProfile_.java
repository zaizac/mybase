/**
 * Copyright 2019. 
 */
package com.idm.model;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * @author mary.jane
 *
 */
@StaticMetamodel(IdmProfile.class)
public class IdmProfile_ {

	public static volatile SingularAttribute<IdmProfile, Integer> userProfId;

	public static volatile SingularAttribute<IdmProfile, String> userId;

	public static volatile SingularAttribute<IdmProfile, String> firstName;

	public static volatile SingularAttribute<IdmProfile, String> lastName;

	public static volatile SingularAttribute<IdmProfile, String> nationalId;

	public static volatile SingularAttribute<IdmProfile, Date> dob;
	
	public static volatile SingularAttribute<IdmProfile, String> gender;
	
	public static volatile SingularAttribute<IdmProfile, String> email;

	public static volatile SingularAttribute<IdmProfile, String> contactNo;

	public static volatile SingularAttribute<IdmProfile, String> cntryCd;

	public static volatile SingularAttribute<IdmProfile, String> position;

	public static volatile SingularAttribute<IdmProfile, IdmUserType> userType;

	public static volatile SingularAttribute<IdmProfile, IdmUserGroup> userRoleGroup;

	public static volatile SingularAttribute<IdmProfile, String> userGroupRoleBranchCode;

	public static volatile SingularAttribute<IdmProfile, Boolean> roleBranch;
	
	public static volatile SingularAttribute<IdmProfile, String> profRegNo;
	
	public static volatile SingularAttribute<IdmProfile, Integer> profId;
	
	public static volatile SingularAttribute<IdmProfile, Integer> branchId;
	
	public static volatile SingularAttribute<IdmProfile, String> docMgtId;
	
	public static volatile SingularAttribute<IdmProfile, Timestamp> lastLogin;

	public static volatile SingularAttribute<IdmProfile, String> authOption;

	public static volatile SingularAttribute<IdmProfile, String> fcmAccessToken;
	
	public static volatile SingularAttribute<IdmProfile, String> activationCode;
	
	public static volatile SingularAttribute<IdmProfile, String> status;
	
	public static volatile SingularAttribute<IdmProfile, Integer> syncFlag;
	
	public static volatile SingularAttribute<IdmProfile, String> systemType;
	
	public static volatile SingularAttribute<IdmProfile, IdmFcm> fcm;
	
	public static volatile SingularAttribute<IdmProfile, String> createId;

	public static volatile SingularAttribute<IdmProfile, Timestamp> createDt;

	public static volatile SingularAttribute<IdmProfile, String> updateId;

	public static volatile SingularAttribute<IdmProfile, Timestamp> updateDt;

}
