/**
 * Copyright 2015. Bestinet Sdn Bhd
 */
package com.be.service;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.be.constants.QualifierConstants;
import com.be.core.AbstractService;
import com.be.core.GenericRepository;
import com.be.dao.BeCtrlGenRepository;
import com.be.model.BeCtrlGen;
import com.be.model.BeCtrlGenPK;
import com.be.sdk.model.IQfCriteria;
import com.be.util.WebUtil;


/**
 * @author Mubarak A
 * @since Feb 14, 2019
 */
@Service(QualifierConstants.BE_CTRL_GEN_SERVICE)
@Scope(QualifierConstants.SCOPE_PROTOTYPE)
@Qualifier(QualifierConstants.BE_CTRL_GEN_SERVICE)
@Transactional
public class BeCtrlGenService extends AbstractService<BeCtrlGen> {

	protected static Logger logger = LoggerFactory.getLogger(BeCtrlGenService.class);

	@Autowired
	private BeCtrlGenRepository beCtrlGenDao;


	@Override
	public GenericRepository<BeCtrlGen> primaryDao() {
		return beCtrlGenDao;
	}


	@Override
	public List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria) {
		return null;
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public synchronized String generateRefNo(String trxnCd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		BeCtrlGenPK pk = new BeCtrlGenPK();
		pk.setCtrlGenDt(sdf.format(new Date()));
		pk.setTrxnCd(trxnCd);
		logger.info("GenDt: {} - TrxnCd: {}", pk.getCtrlGenDt(), pk.getTrxnCd());
		BeCtrlGen ecg = beCtrlGenDao.findByPK(pk);

		if (ecg == null) {
			ecg = new BeCtrlGen();
			ecg.setBeCtrlGenPK(pk);
			ecg.setTrxnId(1);
			ecg.setCreateDt(new Timestamp(new Date().getTime()));
			ecg.setUpdateDt(new Timestamp(new Date().getTime()));
		}

		int trxnId = ecg.getTrxnId();
		ecg.setTrxnId(trxnId + 1);
		beCtrlGenDao.saveAndFlush(ecg);
		return WebUtil.genChksumDigit(ecg.getBeCtrlGenPK().getTrxnCd(), ecg.getBeCtrlGenPK().getCtrlGenDt(),
				trxnId);
	}


	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public synchronized String generateRefNo(String trxnCd, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		BeCtrlGenPK pk = new BeCtrlGenPK();
		pk.setCtrlGenDt(sdf.format(new Date()));
		pk.setTrxnCd(trxnCd);
		logger.info("GenDt: {} - TrxnCd: {}", pk.getCtrlGenDt(), pk.getTrxnCd());
		BeCtrlGen ecg = beCtrlGenDao.findByPK(pk);

		if (ecg == null) {
			ecg = new BeCtrlGen();
			ecg.setBeCtrlGenPK(pk);
			ecg.setTrxnId(1);
			ecg.setCreateDt(new Timestamp(new Date().getTime()));
			ecg.setUpdateDt(new Timestamp(new Date().getTime()));
		}

		int trxnId = ecg.getTrxnId();
		ecg.setTrxnId(trxnId + 1);
		beCtrlGenDao.saveAndFlush(ecg);

		String str = String.valueOf(trxnId);
		StringBuilder sb = new StringBuilder();
		sb.append(trxnCd);
		sb.append(("000000" + str).substring(str.length()));
		return sb.toString();
	}

}