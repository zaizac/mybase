package com.idm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.model.IdmUserGroup;
import com.idm.sdk.model.UserGroup;

@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_USER_GROUP_DAO)
public class IdmUserGroupDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserGroupDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings({ "unchecked"})
	public List<IdmUserGroup> searchUserGroup(UserGroup userGroup){
		StringBuilder sb = new StringBuilder("select i from IdmUserGroup i where 1 = 1 ");
		setSB(userGroup, sb);
		
		Query query = entityManager.createQuery(sb.toString());
		setQuery(userGroup, query);

		return query.getResultList();
	}
	
	private StringBuilder setSB(UserGroup uGroup, StringBuilder sb) {
		
		if(uGroup.getUserGroupCode() != null) {
			sb.append(" and i.userGroupCode =:userGroupCode");
		}
		if(uGroup.getUserGroupDesc() != null) {
			sb.append(" and i.userGroupDesc =:userGroupDesc");
		}
		if(uGroup.getUserTypeCode() != null) {
			sb.append(" and i.userType.userTypeCode =:userTypeCode");
		}
		
		return sb;
	}
	
	private Query setQuery(UserGroup uGroup, Query query) {
		
		if (uGroup.getUserGroupCode() != null) {
			query.setParameter("userGroupCode", uGroup.getUserGroupCode());
		}
		if (uGroup.getUserGroupDesc() != null) {
			query.setParameter("userGroupDesc", uGroup.getUserGroupDesc());
		}
		if (uGroup.getUserTypeCode() != null) {
			query.setParameter("userTypeCode", uGroup.getUserTypeCode());
		}
		
		return query;
		
	}

}
