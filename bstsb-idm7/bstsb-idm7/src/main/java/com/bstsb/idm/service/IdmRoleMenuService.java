/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstsb.idm.config.ConfigConstants;
import com.bstsb.idm.constants.QualifierConstants;
import com.bstsb.idm.core.AbstractService;
import com.bstsb.idm.core.GenericRepository;
import com.bstsb.idm.dao.IdmRoleMenuRepository;
import com.bstsb.idm.model.IdmMenu;
import com.bstsb.idm.model.IdmRoleMenu;
import com.bstsb.idm.model.IdmUserGroupRole;
import com.bstsb.idm.sdk.constants.IdmCacheConstants;
import com.bstsb.util.BaseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Service(QualifierConstants.IDM_ROLEMENU_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLEMENU_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmRoleMenuService extends AbstractService<IdmRoleMenu> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmRoleMenuService.class);

	@Autowired
	private IdmRoleMenuRepository idmRoleMenuRepository;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	@Qualifier(QualifierConstants.IDM_USER_GROUP_ROLES_SERVICE)
	protected IdmUserGroupRoleService idmUserGroupRolesService;


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU.concat(#roleCode)", condition = "#roleCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmRoleMenu> findUserRoleByRoleCode(String roleCode) {
		return idmRoleMenuRepository.findRoleMenuByIdmRoleRoleCode(roleCode);
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmRoleMenu> findRoleMenuByRoleCodeAndMenuCode(String roleCode, String menuCode) {
		return idmRoleMenuRepository.findRoleMenuByIdmRoleRoleCodeAndIdmMenuMenuCode(roleCode, menuCode);
	}


	@SuppressWarnings("unchecked")
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU_GR.concat(#roleRoleGroupCode)", condition = "#roleRoleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmMenu> findRoleMenuByGroupRole(String roleRoleGroupCode) {
		List<IdmUserGroupRole> userRoleGroupList = idmUserGroupRolesService
				.findUserGroupByUserRoleGroupCode(roleRoleGroupCode);
		List<String> roleCodeList = new ArrayList<>();
		// Retrieve ALL Roles for user
		for (IdmUserGroupRole rg : userRoleGroupList) {
			roleCodeList.add(rg.getRoleCode());
		}
		LOGGER.debug("ROLE CODE LIST: {}", new ObjectMapper().valueToTree(roleCodeList));
		List<IdmMenu> menuLst = new ArrayList<>();
		if (!BaseUtil.isListNull(roleCodeList)) {
			List<IdmRoleMenu> roleMenuLst = idmRoleMenuRepository.findRoleMenuByRoleList(roleCodeList);
			if (!BaseUtil.isListNull(roleMenuLst)) {
				Integer maxLevelCnt = idmRoleMenuRepository.maxMenuLevel(roleCodeList);
				Map<String, Object> maxLvlMap = new LinkedHashMap<>();
				maxLvlMap.put("0", new IdmMenu());
				// make unique if multiple role have same menu
				Map<String, IdmMenu> menuMap = new LinkedHashMap<>();
				for (IdmRoleMenu menu : roleMenuLst) {
					menuMap.put(menu.getIdmMenu().getMenuCode(), menu.getIdmMenu());
				}

				// add menu to map
				mapLevelAddMenuMap(maxLvlMap, maxLevelCnt, menuMap);

				// organise Parent / Child menu list
				mapLevelOrganiseParentChildList(maxLvlMap, maxLevelCnt);

				menuLst = (List<IdmMenu>) maxLvlMap.get("1");
			}
		}
		return sortMenuList(menuLst);
	}


	private void mapLevelAddMenuMap(Map<String, Object> maxLvlMap, Integer maxLevelCnt, Map<String, IdmMenu> menuMap) {
		for (int i = 0; i <= maxLevelCnt; i++) {
			List<IdmMenu> maxLvlLst = new ArrayList<>();
			for (Map.Entry<String, IdmMenu> entry : menuMap.entrySet()) {
				IdmMenu menu = entry.getValue();
				if (menu.getMenuLevel() == i) {
					maxLvlLst.add(menu);
				}
			}
			maxLvlMap.put(BaseUtil.getStr(i), maxLvlLst);
		}
	}


	private void mapLevelOrganiseParentChildList(Map<String, Object> maxLvlMap, Integer maxLevelCnt) {
		for (int i = maxLevelCnt; i > 0; i--) {
			@SuppressWarnings("unchecked")
			List<IdmMenu> childLst = (List<IdmMenu>) maxLvlMap.get(BaseUtil.getStr(i));
			int prnt = i - 1;
			@SuppressWarnings("unchecked")
			List<IdmMenu> parentLst = (List<IdmMenu>) maxLvlMap.get(BaseUtil.getStr(prnt));
			List<IdmMenu> newLst = new ArrayList<>();
			for (IdmMenu parent : parentLst) {
				if (BaseUtil.isListNull(parent.getMenuChild())) {
					parent.setMenuChild(new ArrayList<IdmMenu>());
				}
				for (IdmMenu child : childLst) {
					if (BaseUtil.isEqualsCaseIgnore(parent.getMenuCode(), child.getMenuParentCode()) || i == 1) {
						parent.getMenuChild().add(child);
					}
				}
				newLst.add(parent);
			}
			maxLvlMap.put(BaseUtil.getStr(prnt), newLst);
		}
	}


	private List<IdmMenu> sortMenuList(List<IdmMenu> menuLst) {
		if (!BaseUtil.isListNullZero(menuLst)) {
			Collections.sort(menuLst, new Comparator<IdmMenu>() {

				@Override
				public int compare(IdmMenu o1, IdmMenu o2) {
					return o1.getMenuSequence() - o2.getMenuSequence();
				}
			});
		}
		return menuLst;
	}


	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public int maxMenuLevel(String roleCode) {
		List<String> roleCodeList = Arrays.asList(roleCode.split("\\s*,\\s*"));
		LOGGER.debug("roleCodeList: {}", roleCodeList);
		return idmRoleMenuRepository.maxMenuLevel(roleCodeList);
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean bulkInsert(List<IdmRoleMenu> roleLst) {
		try {
			idmRoleMenuRepository.save(roleLst);
			for (IdmRoleMenu role : roleLst) {
				String key = IdmCacheConstants.CACHE_KEY_MENU.concat(role.getIdmRole().getRoleCode());
				cacheManager.getCache(IdmCacheConstants.CACHE_BUCKET).evict(key);
				role.getIdmRole();
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			return false;
		}
	}


	@Override
	public GenericRepository<IdmRoleMenu> primaryDao() {
		return idmRoleMenuRepository;
	}

}