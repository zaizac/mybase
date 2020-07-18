/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.baseboot.report.core;


import org.springframework.data.jpa.domain.Specification;


/**
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public abstract class QueryFactory<T> {

	public abstract Specification<T> searchByProperty(T t);

}
