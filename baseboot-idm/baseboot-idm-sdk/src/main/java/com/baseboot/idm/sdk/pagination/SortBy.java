/**
 * Copyright 2017. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.pagination;


import java.util.HashMap;
import java.util.Map;


/**
 * @author Mary Jane Buenaventura
 * @since 24/10/2017
 */
public class SortBy {

	/** The map of sorts. */
	private Map<String, SortOrder> mapOfSorts;


	/**
	 * Instantiates a new sort by.
	 */
	public SortBy() {
		if (null == mapOfSorts) {
			mapOfSorts = new HashMap<>();
		}
	}


	/**
	 * Gets the sort bys.
	 *
	 * @return the sortBys
	 */
	public Map<String, SortOrder> getSortBys() {
		return mapOfSorts;
	}


	/**
	 * Adds the sort.
	 *
	 * @param sortBy
	 *             the sort by
	 */
	public void addSort(String sortBy) {
		mapOfSorts.put(sortBy, SortOrder.ASC);
	}


	/**
	 * Adds the sort.
	 *
	 * @param sortBy
	 *             the sort by
	 * @param sortOrder
	 *             the sort order
	 */
	public void addSort(String sortBy, SortOrder sortOrder) {
		mapOfSorts.put(sortBy, sortOrder);
	}

}
