/**
 * Copyright 2017. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.pagination;


import java.util.Map;

import com.baseboot.idm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since 24/10/2017
 */
public class DataTableColumnSpecs {

	private static final String COLUMNS_PREFIX = "columns[";

	private int index;

	private String data;

	private String name;

	private boolean searchable;

	private boolean orderable;

	private String search;

	private boolean regex;

	private String sortDir;


	/**
	 * Instantiates a new data table column specs.
	 *
	 * @param request
	 *             the request
	 * @param i
	 *             the i
	 */
	public DataTableColumnSpecs(Map<String, String[]> paramMap, int i) {
		setIndex(i);
		prepareColumnSpecs(paramMap, i);
	}


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}


	/**
	 * Sets the data.
	 *
	 * @param data
	 *             the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name
	 *             the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Checks if is searchable.
	 *
	 * @return the searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}


	/**
	 * Sets the searchable.
	 *
	 * @param searchable
	 *             the searchable to set
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}


	/**
	 * Checks if is orderable.
	 *
	 * @return the orderable
	 */
	public boolean isOrderable() {
		return orderable;
	}


	/**
	 * Sets the orderable.
	 *
	 * @param orderable
	 *             the orderable to set
	 */
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}


	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}


	/**
	 * Sets the search.
	 *
	 * @param search
	 *             the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}


	/**
	 * Checks if is regex.
	 *
	 * @return the regex
	 */
	public boolean isRegex() {
		return regex;
	}


	/**
	 * Sets the regex.
	 *
	 * @param regex
	 *             the regex to set
	 */
	public void setRegex(boolean regex) {
		this.regex = regex;
	}


	/**
	 * Gets the sort dir.
	 *
	 * @return the sortDir
	 */
	public String getSortDir() {
		return sortDir;
	}


	/**
	 * Sets the sort dir.
	 *
	 * @param sortDir
	 *             the sortDir to set
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = (null != sortDir) ? sortDir.toUpperCase() : sortDir;
	}


	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}


	/**
	 * Sets the index.
	 *
	 * @param index
	 *             the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}


	/**
	 * Prepare column specs.
	 *
	 * @param request
	 *             the request
	 * @param i
	 *             the i
	 */
	private void prepareColumnSpecs(Map<String, String[]> paramMap, int i) {
		String sort = null;
		int sortableCol = 0;

		for (Map.Entry<String, String[]> key : paramMap.entrySet()) {
			String[] value = key.getValue();

			if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][data]")) {
				setData(value[0]);
			} else if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][name]")) {
				setName(value[0]);
			} else if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][orderable]")) {
				setOrderable(Boolean.valueOf(value[0]));
			} else if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][search][regex]")) {
				setRegex(Boolean.valueOf(value[0]));
			} else if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][search][value]")) {
				setSearch(value[0]);
			} else if (BaseUtil.isEquals(key.getKey(), COLUMNS_PREFIX + i + "][searchable]")) {
				setSearchable(Boolean.valueOf(value[0]));
			} else if (BaseUtil.isEquals(key.getKey(), "order[0][column]")) {
				sortableCol = Integer.parseInt(value[0]);
			} else if (BaseUtil.isEquals(key.getKey(), "order[0][dir]")) {
				sort = value[0];
			}
		}

		if (i == sortableCol) {
			setSortDir(sort);
		}
	}

}
