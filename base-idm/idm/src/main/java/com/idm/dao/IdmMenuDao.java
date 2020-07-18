package com.idm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.idm.constants.QualifierConstants;
import com.idm.model.IdmMenu;
import com.idm.sdk.model.UserMenu;

@Repository
@Transactional
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_MENU_DAO)
public class IdmMenuDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdmMenuDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings({ "unchecked"})
	public List<IdmMenu> findMenuByCodeEtc(UserMenu userMenu){
		StringBuilder sb = new StringBuilder("select i from IdmMenu i where 1 = 1 ");
		setSB(userMenu, sb);
		
		Query query = entityManager.createQuery(sb.toString());
		setQuery(userMenu, query);

		return query.getResultList();
	}
	
	private StringBuilder setSB(UserMenu uMenu, StringBuilder sb) {
		
		if(uMenu.getMenuCode() != null) {
			sb.append(" and i.menuCode =:menuCode");
		}
		if(uMenu.getMenuDesc() != null) {
			sb.append(" and i.menuDesc =:menuDesc");
		}
		if(uMenu.getMenuLevel() != 0) {
			sb.append(" and i.menuLevel =:menuLevel");
		}
		if(uMenu.getMenuParentCode() != null) {
			sb.append(" and i.menuParentCode =:menuParentCode");
		}
		
		return sb;
	}
	
	@SuppressWarnings("unused")
	private Query setQuery(UserMenu uMenu, Query query) {
		
		if (uMenu.getMenuCode() != null) {
			query.setParameter("menuCode", uMenu.getMenuCode());
		}
		if (uMenu.getMenuDesc() != null) {
			query.setParameter("menuDesc", uMenu.getMenuDesc());
		}
		if (uMenu.getMenuLevel() != 0) {
			query.setParameter("menuLevel", uMenu.getMenuLevel());
		}
		if (uMenu.getMenuParentCode() != null) {
			query.setParameter("menuParentCode", uMenu.getMenuParentCode());
		}
		
		return query;
		
	}

}
