package com.idm.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * @author mary.jane
 *
 */
@StaticMetamodel(IdmUserGroup.class)
public class IdmUserGroup_ {

	public static SingularAttribute<IdmUserGroup, String> userGroupCode;

	public static SingularAttribute<IdmUserGroup, String> userGroupDesc;

	public static SingularAttribute<IdmUserGroup, IdmUserType> userType;

	public static SingularAttribute<IdmUserGroup, String> createId;

	public static SingularAttribute<IdmUserGroup, Timestamp> createDt;

	public static SingularAttribute<IdmUserGroup, String> updateId;

	public static SingularAttribute<IdmUserGroup, Timestamp> updateDt;

	public static ListAttribute<IdmUserGroup, List<IdmProfile>> idmProfileLst;
	
	public static ListAttribute<IdmUserGroup, List<IdmUserGroupRole>> idmUserGroupRoles;

}
