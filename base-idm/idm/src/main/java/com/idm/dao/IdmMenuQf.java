/**
 * 
 */
package com.idm.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmMenu;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;

/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_MENU_QF)
public class IdmMenuQf extends QueryFactory<IdmMenu> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdmMenuQf.class);

	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;
	
	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}
	
	@Override
	public Specification<IdmMenu> searchByProperty(IdmMenu t) {
		return (Root<IdmMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}

	@Override
	public List<IdmMenu> searchAllByProperty(IdmMenu t) {
		CriteriaQuery<IdmMenu> cq = cb.createQuery(IdmMenu.class);
		Root<IdmMenu> from = cq.from(IdmMenu.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmMenu dto = JsonUtil.transferToObject(criteria, IdmMenu.class);
	
			if (!BaseUtil.isObjNull(dto.getMenuCode())) {
				predicates.add(cb.equal(from.get("menuCode"), dto.getMenuCode()));
			}
			
			if (!BaseUtil.isObjNull(dto.getMenuParentCode())) {
				predicates.add(cb.equal(from.get("menuParentCode"), dto.getMenuParentCode()));
			}
			
			if (!BaseUtil.isObjNull(dto.getMenuLevel())) {
				predicates.add(cb.equal(from.get("menuLevel"), dto.getMenuLevel()));
			}
			
			if (!BaseUtil.isObjNull(dto.getMenuSequence())) {
				predicates.add(cb.equal(from.get("menuSequence"), dto.getMenuSequence()));
			}
			
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}
	
		return predicates;
	}

}
