/**
 * Copyright 2019
 */
package com.be.core;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.be.sdk.model.IQfCriteria;
import com.util.BaseUtil;
import com.util.pagination.PaginationCriteria;
import com.util.pagination.SortOrder;


/**
 * @author Mary Jane Buenaventura
 * @since Nov 17, 2016
 */
public abstract class QueryFactory<T> {

	public abstract Specification<T> searchByProperty(T t);


	public abstract List<T> searchAllByProperty(T t);


	public abstract List<Predicate> generateCriteria(CriteriaBuilder cb, From<?, ?> from, IQfCriteria<?> criteria);


	protected List<Order> getOrderByClause(CriteriaBuilder cb, From<?, ?> from, PaginationCriteria pagination) {
		List<Order> orderList = new ArrayList<>();
		if (!pagination.isSortByEmpty()) {
			Iterator<Entry<String, SortOrder>> sbit = pagination.getSortBy().getSortBys().entrySet().iterator();
			while (sbit.hasNext()) {
				Map.Entry<String, SortOrder> pair = sbit.next();
				if (BaseUtil.isEqualsCaseIgnoreAny("asc", pair.getValue().toString())) {
					orderList.add(cb.asc(from.get(pair.getKey())));
				} else if (BaseUtil.isEqualsCaseIgnoreAny("desc", pair.getValue().toString())) {
					orderList.add(cb.desc(from.get(pair.getKey())));
				}
			}
		}
		return orderList;
	}

}
