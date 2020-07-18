package com.idm.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idm.config.ConfigConstants;
import com.idm.constants.QualifierConstants;
import com.idm.core.AbstractService;
import com.idm.core.GenericRepository;
import com.idm.dao.IdmMenuDao;
import com.idm.dao.IdmMenuQf;
import com.idm.dao.IdmMenuRepository;
import com.idm.model.IdmMenu;
import com.idm.sdk.constants.IdmCacheConstants;
import com.idm.sdk.model.UserMenu;
import com.util.model.IQfCriteria;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Transactional(QualifierConstants.TRANS_MANAGER)
@Service(QualifierConstants.IDM_MENU_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_MENU_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmMenuService extends AbstractService<IdmMenu> {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdmUserGroupService.class);

	@Autowired
	private IdmMenuRepository idmMenuRepository;
	
	@Autowired
	private IdmMenuDao idmMenuDao;
	
	@Autowired
	private IdmMenuQf idmMenuQf;

	@Override
	public GenericRepository<IdmMenu> primaryDao() {
		return idmMenuRepository;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return idmMenuQf.generateCriteria(cb, from, criteria);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU.concat(#menuCode)", condition = "#menuCode != null and #result != null")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public IdmMenu findMenuByMenuCode(String menuCode) {
		return idmMenuRepository.findByMenuCode(menuCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU_ALL", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmMenu> findAllMenus() {
		return idmMenuRepository.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU_LST", condition = "#parentCode != null", unless = "#result != null and #result.size() == 0")
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmMenu> findByMenuParentCode(String parentCode) {
		return idmMenuRepository.findByMenuParentCode(parentCode);
	}


	public List<IdmMenu> findMenuByMenuLevel(Integer menuLevel) {
		return idmMenuRepository.findByMenuLevel(menuLevel);
	}


	@Override
	@Caching(evict = {
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_MENU_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_MENU_LST.concat(#s.menuParentCode)"), }, put = {
							@CachePut(key = ConfigConstants.CACHE_JAVA_FILE
									+ ".CACHE_KEY_MENU.concat(#s.menuCode)") })
	public IdmMenu create(IdmMenu s) {
		return super.create(s);
	}


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_MENU_ALL"),
			@CacheEvict(beforeInvocation = true, key = ConfigConstants.CACHE_JAVA_FILE
					+ ".CACHE_KEY_MENU_LST.concat(#s.menuParentCode)"),
			@CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_ROLE.concat(#s.menuCode)") })
	public IdmMenu update(IdmMenu s) {
		return super.update(s);
	}


	public boolean delete(IdmMenu idmMenu) {
		try {
			this.primaryDao().delete(idmMenu);
			return true;
		} catch (Exception e) {
			LOGGER.error("IdmMenu : DELETE {} : {}", idmMenu.getMenuCode(), e);
			return false;
		}
	}
	
	public Integer findByMaxMenuSequence(String menuParentCode) {
		return idmMenuRepository.findByMaxMenuSequence(menuParentCode);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<IdmMenu> searchIdmMenu(UserMenu userMenu){
		return idmMenuDao.findMenuByCodeEtc(userMenu);
	}
}