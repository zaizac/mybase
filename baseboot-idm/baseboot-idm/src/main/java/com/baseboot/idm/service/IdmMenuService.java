/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.idm.service;


import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baseboot.idm.constants.ConfigConstants;
import com.baseboot.idm.constants.QualifierConstants;
import com.baseboot.idm.core.AbstractService;
import com.baseboot.idm.core.GenericRepository;
import com.baseboot.idm.dao.IdmMenuRepository;
import com.baseboot.idm.model.IdmMenu;
import com.baseboot.idm.sdk.constants.IdmCacheConstants;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@Lazy
@Transactional
@Service(QualifierConstants.IDMMENU_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDMMENU_SERVICE)
@CacheConfig(cacheNames = IdmCacheConstants.CACHE_BUCKET)
public class IdmMenuService extends AbstractService<IdmMenu> {

	@Lazy
	@Autowired
	private IdmMenuRepository idmMenuRepository;


	@Override
	public GenericRepository<IdmMenu> primaryDao() {
		return idmMenuRepository;
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU.concat(#menuCode)", condition = "#menuCode != null and #result != null")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public IdmMenu findMenuByMenuCode(String menuCode) {
		return idmMenuRepository.findMenuByMenuCode(menuCode);
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU_ALL", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmMenu> findAllMenus() {
		return idmMenuRepository.findAll();
	}


	@Cacheable(key = ConfigConstants.CACHE_JAVA_FILE
			+ ".CACHE_KEY_MENU_LST", condition = "#parentCode != null", unless = "#result != null and #result.size() == 0")
	@javax.transaction.Transactional(value = TxType.SUPPORTS, rollbackOn = Exception.class)
	public List<IdmMenu> findMenuByParentCode(String parentCode) {
		return idmMenuRepository.findAllMenuByPerentCode(parentCode);
	}


	public List<IdmMenu> findMenuByMenuLevel(Integer menuLevel) {
		return idmMenuRepository.findMenuByMenuLevel(menuLevel);
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


	@Override
	@Caching(evict = { @CacheEvict(key = ConfigConstants.CACHE_JAVA_FILE + ".CACHE_KEY_MENU_ALL"), })
	public boolean delete(Integer id) {
		return super.delete(id);
	}
}