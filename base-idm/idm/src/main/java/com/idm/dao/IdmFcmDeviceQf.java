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
import com.idm.controller.MobileRestController;
import com.idm.core.QueryFactory;
import com.idm.model.IdmFcm;
import com.idm.model.IdmFcmDevice;
import com.idm.sdk.constants.IdmErrorCodeEnum;
import com.idm.sdk.exception.IdmException;
import com.idm.service.IdmFcmService;
import com.util.BaseUtil;
import com.util.JsonUtil;
import com.util.model.IQfCriteria;

/**
 * @author mary.jane
 *
 */
@Repository
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.IDM_FCM_DEVICE_QF)
public class IdmFcmDeviceQf extends QueryFactory<IdmFcmDevice> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdmFcmDeviceQf.class);

	@Lazy
	@Autowired
	private IdmFcmService idmFcmSvc;
	
	@PersistenceContext
	private EntityManager em;

	private CriteriaBuilder cb;


	@PostConstruct
	private void init() {
		cb = em.getCriteriaBuilder();
	}


	@Override
	public Specification<IdmFcmDevice> searchByProperty(IdmFcmDevice t) {
		return (Root<IdmFcmDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predLst = generateCriteria(cb, root, t);
			LOGGER.info("PREDLIST: {}", predLst.size());
			if (predLst != null && !CollectionUtils.isEmpty(predLst)) {
				return query.where(predLst.toArray(new Predicate[predLst.size()])).getRestriction();
			}

			return query.getRestriction();
		};
	}


	@Override
	public List<IdmFcmDevice> searchAllByProperty(IdmFcmDevice t) {
		CriteriaQuery<IdmFcmDevice> cq = cb.createQuery(IdmFcmDevice.class);
		Root<IdmFcmDevice> from = cq.from(IdmFcmDevice.class);
		List<Predicate> predicates = generateCriteria(cb, from, t);
		cq.select(from).distinct(true);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(cq).getResultList();
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		List<Predicate> predicates = new ArrayList<>();
		try {
			IdmFcmDevice dto = JsonUtil.transferToObject(criteria, IdmFcmDevice.class);

			LOGGER.info("FcmDevice getDeviceId: {}", dto.getDeviceId());
			if (!BaseUtil.isObjNull(dto.getDeviceId())) {
				predicates.add(cb.equal(from.get("deviceId"), dto.getDeviceId()));
			}
			
			LOGGER.info("FcmDevice getFcmToken: {}", dto.getFcmToken());
			if (!BaseUtil.isObjNull(dto.getFcmToken())) {
				predicates.add(cb.equal(from.get("fcmToken"), dto.getFcmToken()));
			}
			
			LOGGER.info("FcmDevice getMachineId: {}", dto.getMachineId());
			if (!BaseUtil.isObjNull(dto.getMachineId())) {
				predicates.add(cb.equal(from.get("machineId"), dto.getMachineId()));
			}
			
			LOGGER.info("FcmDevice getStatus: {}", dto.getStatus());
			if (!BaseUtil.isObjNull(dto.getStatus())) {
				predicates.add(cb.equal(from.get("status"), dto.getStatus()));
			}

			if (!BaseUtil.isObjNull(dto.getFcm())) {
				LOGGER.info("FcmDevice getUserId: {}", dto.getFcm().getUserId());
				dto.getFcm().setFcmDevices(null);
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Join<IdmFcmDevice, IdmFcm> ugJoin = (Join) from.fetch("fcm", JoinType.LEFT);
				predicates.addAll(idmFcmSvc.generateCriteria(cb, ugJoin, dto.getFcm()));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new IdmException(IdmErrorCodeEnum.E400IDM914);
		}

		return predicates;
	}

}
