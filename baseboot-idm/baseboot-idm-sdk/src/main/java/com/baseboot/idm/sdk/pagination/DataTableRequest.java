/**
 * Copyright 2017. Bestinet Sdn Bhd
 */
package com.baseboot.idm.sdk.pagination;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baseboot.idm.sdk.util.BaseUtil;


/**
 * @author Mary Jane Buenaventura
 * @since 24/10/2017
 */
public class DataTableRequest<E> {

	/** The unique id. */
	private String uniqueId;

	/** The draw. */
	private String draw;

	/** The start. */
	private Integer start;

	/** The length. */
	private Integer length;

	/** The search. */
	private String search;

	/** The regex. */
	private boolean regex;

	/** The columns. */
	private List<DataTableColumnSpecs> columns;

	/** The order. */
	private DataTableColumnSpecs order;

	/** The is global search. */
	private boolean isGlobalSearch;

	private boolean initSearch;


	/**
	 * Instantiates a new data table request.
	 *
	 * @param request
	 *             the request
	 */
	public DataTableRequest(Map<String, String[]> paramMap) {
		prepareDataTableRequest(paramMap);
	}


	/**
	 * Gets the unique id.
	 *
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}


	/**
	 * Sets the unique id.
	 *
	 * @param uniqueId
	 *             the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}


	/**
	 * Sets the start.
	 *
	 * @param start
	 *             the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}


	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}


	/**
	 * Sets the length.
	 *
	 * @param length
	 *             the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
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
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<DataTableColumnSpecs> getColumns() {
		return columns;
	}


	/**
	 * Sets the columns.
	 *
	 * @param columns
	 *             the columns to set
	 */
	public void setColumns(List<DataTableColumnSpecs> columns) {
		this.columns = columns;
	}


	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public DataTableColumnSpecs getOrder() {
		return order;
	}


	/**
	 * Sets the order.
	 *
	 * @param order
	 *             the order to set
	 */
	public void setOrder(DataTableColumnSpecs order) {
		this.order = order;
	}


	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public String getDraw() {
		return draw;
	}


	/**
	 * Sets the draw.
	 *
	 * @param draw
	 *             the draw to set
	 */
	public void setDraw(String draw) {
		this.draw = draw;
	}


	/**
	 * Checks if is global search.
	 *
	 * @return the isGlobalSearch
	 */
	public boolean isGlobalSearch() {
		return isGlobalSearch;
	}


	/**
	 * Sets the global search.
	 *
	 * @param isGlobalSearch
	 *             the isGlobalSearch to set
	 */
	public void setGlobalSearch(boolean isGlobalSearch) {
		this.isGlobalSearch = isGlobalSearch;
	}


	public boolean isInitSearch() {
		return initSearch;
	}


	public void setInitSearch(boolean isInitSearch) {
		initSearch = isInitSearch;
	}


	/**
	 * Prepare data table request.
	 *
	 * @param request
	 *             the request
	 */
	private void prepareDataTableRequest(Map<String, String[]> paramMap) {
		if (BaseUtil.isObjNull(paramMap)) {
			return;
		}

		int sortableCol = 0;

		for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
			String[] value = param.getValue();
			switch (param.getKey()) {
			case PaginationCriteria.PAGE_NO:
				setStart(Integer.parseInt(value[0]));
				break;
			case PaginationCriteria.PAGE_SIZE:
				setLength(Integer.parseInt(value[0]));
				break;
			case "_":
				setUniqueId(value[0]);
				break;
			case PaginationCriteria.DRAW:
				setDraw(value[0]);
				break;
			case "search[value]":
				setSearch(value[0]);
				break;
			case "search[regex]":
				setRegex(Boolean.valueOf(value[0]));
				break;
			case "order[0][column]":
				sortableCol = Integer.parseInt(value[0]);
				break;
			case PaginationCriteria.INIT_SEARCH:
				setInitSearch(Boolean.valueOf(value[0]));
				break;
			default:
				break;
			}
		}

		List<DataTableColumnSpecs> dtColumns = new ArrayList<>();

		if (!BaseUtil.isObjNull(getSearch())) {
			setGlobalSearch(true);
		}

		int maxParamsToCheck = getNumberOfColumns(paramMap);

		for (int i = 0; i < maxParamsToCheck; i++) {
			if (null != paramMap.get("columns[" + i + "][data]")
					&& !BaseUtil.isObjNull(paramMap.get("columns[" + i + "][data]"))) {
				DataTableColumnSpecs colSpec = new DataTableColumnSpecs(paramMap, i);
				if (i == sortableCol) {
					setOrder(colSpec);
				}
				dtColumns.add(colSpec);

				if (!BaseUtil.isObjNull(colSpec.getSearch())) {
					setGlobalSearch(false);
				}
			}
		}

		if (!BaseUtil.isObjNull(dtColumns)) {
			setColumns(dtColumns);
		}
	}


	private int getNumberOfColumns(Map<String, String[]> paramMap) {
		Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");
		List<String> lstOfParams = new ArrayList<>();
		for (String key : paramMap.keySet()) {
			Matcher m = p.matcher(key);
			if (m.matches()) {
				lstOfParams.add(key);
			}
		}
		return lstOfParams.size();
	}


	/**
	 * Gets the pagination request.
	 *
	 * @return the pagination request
	 */
	public PaginationCriteria getPaginationRequest() {
		PaginationCriteria pagination = new PaginationCriteria();
		pagination.setPageNumber(getStart());
		pagination.setPageSize(getLength());

		SortBy sortBy = null;
		if (!BaseUtil.isObjNull(getOrder())) {
			sortBy = new SortBy();
			if (!BaseUtil.isObjNull(getOrder())) {
				sortBy.addSort(getOrder().getData(), SortOrder.fromValue(getOrder().getSortDir()));
			}
		}

		FilterBy filterBy = new FilterBy();
		filterBy.setGlobalSearch(isGlobalSearch());
		for (DataTableColumnSpecs colSpec : getColumns()) {
			if (colSpec.isSearchable() && !BaseUtil.isObjNull(getSearch())
					|| !BaseUtil.isObjNull(colSpec.getSearch())) {
				filterBy.addFilter(colSpec.getData(), (isGlobalSearch()) ? getSearch() : colSpec.getSearch());
			}
		}

		pagination.setSortBy(sortBy);
		pagination.setFilterBy(filterBy);

		return pagination;
	}

}