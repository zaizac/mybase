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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.idm.constants.QualifierConstants;
import com.idm.core.QueryFactory;
import com.idm.model.IdmFcm;
import com.idm.model.IdmFcmDevice;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.service.IdmFcmDeviceService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;

/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_QF)
public class IdmFcmQf extends QueryFactory<IdmFcm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdmFcmQf.class);

	@Lazy
	@Autowired
	private IdmFcmDeviceService idmFcmDeviceSvc;
	
	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmFcm> searchByProperty(IdmFcm t) {
		return (Root<IdmFcm> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);

			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}

	
	@Override
	public List<IdmFcm> searchAllByProperty(IdmFcm t) {
		CriteriaQuery<IdmFcm> cq = cb.createQuery(IdmFcm.class);
		Root<IdmFcm> from = cq.from(IdmFcm.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}
	
	
	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmFcm dto = JsonUtil.transferToObject(criteria, IdmFcm.class);
	
			LOGGER.info("Fcm getUserId: {}", dto.getUserId());
			if (!BaseUtil.isObjNull(dto.getUserId())) {
				predicates.add(cb.equal(from.get("userId"), dto.getUserId()));
			}
			
			LOGGER.info("Fcm getPinCd: {}", dto.getPinCd());
			if (!BaseUtil.isObjNull(dto.getPinCd())) {
				predicates.add(cb.equal(from.get("pinCd"), dto.getPinCd()));
			}
			
			LOGGER.info("Fcm getFaceId: {}", dto.getFaceId());
			if (!BaseUtil.isObjNull(dto.getFaceId())) {
				predicates.add(cb.equal(from.get("faceId"), dto.getFaceId()));
			}
			
			LOGGER.info("Fcm getStatus: {}", dto.getStatus());
			if (!BaseUtil.isObjNull(dto.getStatus())) {
				predicates.add(cb.equal(from.get("status"), dto.getStatus()));
			}
	
			if (!BaseUtil.isObjNull(dto.getFcmDevices())) {
				dto.getFcmDevices().forEach(t -> {			
					t.setFcm(null);
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Join<IdmFcm, IdmFcmDevice> ugJoin = (Join) from.fetch("fcmDevices", JoinType.LEFT);
					predicates.addAll(idmFcmDeviceSvc.generateCriteria(cb, ugJoin, t));
				});
			}
			
		} catch (IOException e) {
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}
	
		return predicates;
	}

}
