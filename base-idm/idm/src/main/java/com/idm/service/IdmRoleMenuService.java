package com.idm.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmRoleMenuQf;
import com.idm.dao.IdmRoleMenuRepository;
import com.idm.model.IdmMenu;
import com.idm.model.IdmRoleMenu;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.exception.IdmException;
import com.idm.sdk.model.RoleMenu;
import com.idm.sdk.model.UserMenu;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;
import com.util.pagination.DataTableRequest;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_ROLEMENU_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_ROLEMENU_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmRoleMenuService extends AbstractService<IdmRoleMenu> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmRoleMenuService.class);

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private IdmRoleMenuRepository idmRoleMenuRepository;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	protected IdmUserGroupRoleService idmUserGroupRoleSvc;
	
	@Autowired
	private IdmRoleMenuQf idmRoleMenuQf;


	@Override
	public GenericRepository<IdmRoleMenu> primaryDao() {
		return idmRoleMenuRepository;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}
	
	@Override
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU", unless = "#result != null and #result.size() == 0")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public List<IdmRoleMenu> findAll() {
		return idmRoleMenuRepository.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmRoleMenu> findByPortalTypeCode(String portalTypeCode) {
		return idmRoleMenuRepository.findByIdmPortalTypePortalTypeCode(portalTypeCode);
	}	

	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU.concat(#roleCode)", condition = "#roleCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmRoleMenu> findByIdmRoleRoleCode(String roleCode) {
		return idmRoleMenuRepository.findByIdmRoleRoleCode(roleCode);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmRoleMenu> findRoleMenuByMenuCode(String menuCode) {
		return idmRoleMenuRepository.findByIdmMenuMenuCode(menuCode);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmRoleMenu> findRoleMenuByRoleCodeAndMenuCode(String roleCode, String menuCode) {
		return idmRoleMenuRepository.findRoleMenuByIdmRoleRoleCodeAndIdmMenuMenuCode(roleCode, menuCode);
	}


	@SuppressWarnings("unchecked")
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU_GR.concat(#roleGroupCode)", condition = "#roleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserMenu> findRoleMenuByGroupRole(String roleGroupCode) {
		return findRoleMenuByPortalTypeAndGroupRole(null, roleGroupCode);
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_ROLE_MENU_GR.concat(#roleGroupCode)", condition = "#roleGroupCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserMenu> findRoleMenuByPortalTypeAndGroupRole(String portalType, String roleGroupCode) {
		// Retrieve ALL Roles for user
		List<String> roleCodeList = idmUserGroupRoleSvc.findAllRolesByUserRoleGroupCode(roleGroupCode);

		LOGGER.info("ROLE CODE LIST: {}", JsonUtil.objectMapper(true).valueToTree(roleCodeList));
		List<UserMenu> menuLst = new ArrayList<>();
		if (!BaseUtil.isListNull(roleCodeList)) {
			LOGGER.info("Portal Type: {}", portalType);
			List<IdmRoleMenu> roleMenuLst = null;
			if(!BaseUtil.isObjNull(portalType)) {
				roleMenuLst =  idmRoleMenuRepository.findRoleMenuByPortalTypeCodeAndRoleList(portalType, roleCodeList);
			} else {
				roleMenuLst = idmRoleMenuRepository.findRoleMenuByRoleList(roleCodeList);
			}
			try {
				LOGGER.debug("roleMenuLst: {}", JsonUtil.objectMapper(true).writeValueAsString(roleMenuLst));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
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
				try {
					mapLevelOrganiseParentChildList(maxLvlMap, maxLevelCnt);
				} catch (IOException e1) {
					// DO NOTHING
				}

				try {
					LOGGER.debug("mapLevelOrganiseParentChildList: {}",
							JsonUtil.objectMapper(true).writeValueAsString(maxLvlMap));
					menuLst = JsonUtil.transferToList((List<UserMenu>) maxLvlMap.get("1"), UserMenu.class);
				} catch (IOException e) {
					// DO NOTHING
				}
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


	private void mapLevelOrganiseParentChildList(Map<String, Object> maxLvlMap, Integer maxLevelCnt)
			throws IOException {
		for (int i = maxLevelCnt; i > 0; i--) {
			@SuppressWarnings("unchecked")
			List<UserMenu> childLst = JsonUtil.transferToList((List<IdmMenu>) maxLvlMap.get(BaseUtil.getStr(i)),
					UserMenu.class);
			int prnt = i - 1;
			@SuppressWarnings("unchecked")
			List<UserMenu> parentLst = JsonUtil.transferToList((List<IdmMenu>) maxLvlMap.get(BaseUtil.getStr(prnt)),
					UserMenu.class);
			List<UserMenu> newLst = new ArrayList<>();
			for (UserMenu parent : parentLst) {
				if (BaseUtil.isListNull(parent.getMenuChild())) {
					parent.setMenuChild(new ArrayList<UserMenu>());
				}
				for (UserMenu child : childLst) {
					if (BaseUtil.isEqualsCaseIgnore(parent.getMenuCode(), child.getMenuParentCode()) || i == 1) {
						parent.getMenuChild().add(child);
					}
				}
				newLst.add(parent);
			}
			maxLvlMap.put(BaseUtil.getStr(prnt), newLst);
		}
	}


	private List<UserMenu> sortMenuList(List<UserMenu> menuLst) {
		if (!BaseUtil.isListNullZero(menuLst)) {
			Collections.sort(menuLst, (UserMenu o1, UserMenu o2) -> {
				return o1.getMenuSequence() - o2.getMenuSequence();
			});
		}
		return menuLst;
	}


	@Transactional(readOnly = true, rollbackFor = Exception.class)
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
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RoleMenu> searchByPagination(RoleMenu t, DataTableRequest<?> dataTableInRQ) {
		try {
			List<IdmRoleMenu> result = idmRoleMenuQf.searchByPagination(t, dataTableInRQ);
			return JsonUtil.transferToList(result, RoleMenu.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdmException(e.getMessage());
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmRoleMenu findByRoleMenuId(Integer id) {
		return idmRoleMenuRepository.findByRoleMenuId(id);
	}

}