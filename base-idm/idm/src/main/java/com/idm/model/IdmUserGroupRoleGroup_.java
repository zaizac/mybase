package com.idm.model;


import java.sql.Timestamp;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(IdmUserGroupRoleGroup.class)
public class IdmUserGroupRoleGroup_ {

	public static SingularAttribute<IdmUserGroupRoleGroup, Integer> groupId;

	public static SingularAttribute<IdmUserGroupRoleGroup, String> userGroupCode;

	public static SingularAttribute<IdmUserGroupRoleGroup, String> parentRoleGroup;

	public static SingularAttribute<IdmUserGroupRoleGroup, IdmUserGroup> userRoleGroup;

	public static SingularAttribute<IdmUserGroupRoleGroup, String> userTypeCode;

	public static SingularAttribute<IdmUserGroupRoleGroup, Integer> maxNoOfUser;

	public static SingularAttribute<IdmUserGroupRoleGroup, Boolean> createByProfId;
	
	public static SingularAttribute<IdmUserGroupRoleGroup, Boolean> createByBranchId;
	
	public static SingularAttribute<IdmUserGroupRoleGroup, Boolean> systemCreate;

	public static SingularAttribute<IdmUserGroupRoleGroup, Boolean> selCountry;
	
	public static SingularAttribute<IdmUserGroupRoleGroup, Boolean> selBranch;
	
	public static SingularAttribute<IdmUserGroupRoleGroup, String> cntryCd;

	public static SingularAttribute<IdmUserGroupRoleGroup, String> systemType;
	
	public static SingularAttribute<IdmUserGroupRoleGroup, String> createId;

	public static SingularAttribute<IdmUserGroupRoleGroup, Timestamp> createDt;

	public static SingularAttribute<IdmUserGroupRoleGroup, String> updateId;

	public static SingularAttribute<IdmUserGroupRoleGroup, Timestamp> updateDt;

}
