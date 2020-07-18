/**
 * Copyright 2019. 
 */
package com.util.pagination;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.util.BaseUtil;


/**
 * The Class DataTableResults.
 *
 * @author Mary Jane Buenaventura
 * @param <T>
 *             the generic type
 * @since 24/10/2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTableResults<T> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataTableResults.class);

	/** The draw. */
	private String draw;

	/** The records filtered. */
	private String recordsFiltered;

	/** The records total. */
	private String recordsTotal;

	/** The list of data objects. */
	List<T> data;

	/** The error. */
	private String error;


	public DataTableResults() {
		// DO NOTHING
	}


	public DataTableResults(DataTableRequest<T> dataTableInRQ, int totalRecord, List<T> filteredList) {
		int filteredRecords = !filteredList.isEmpty() ? filteredList.size() : 0;
		this.draw = dataTableInRQ.getDraw();
		this.recordsFiltered = Integer.toString(filteredRecords);
		if (!BaseUtil.isObjNull(dataTableInRQ)) {
			long totalRecords = dataTableInRQ.isInitSearch() ? totalRecord : filteredRecords;
			this.recordsTotal = BaseUtil.getStr(totalRecords);
			//if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				this.recordsFiltered = BaseUtil.getStr(totalRecords);
			//} 
		}
		this.data = filteredList;
	}


	/**
	 * Gets the json.
	 *
	 * @return the json
	 */
	public String getJson() {
		String json = null;
		try {
			json = new Gson().toJson(this);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return json;
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
	 * Gets the records filtered.
	 *
	 * @return the recordsFiltered
	 */
	public String getRecordsFiltered() {
		return recordsFiltered;
	}


	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered
	 *             the recordsFiltered to set
	 */
	public void setRecordsFiltered(String recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}


	/**
	 * Gets the records total.
	 *
	 * @return the recordsTotal
	 */
	public String getRecordsTotal() {
		return recordsTotal;
	}


	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal
	 *             the recordsTotal to set
	 */
	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}


	/**
	 * Gets the list of data objects.
	 *
	 * @return the listOfDataObjects
	 */
	public List<T> getData() {
		return data;
	}


	/**
	 * Sets the list of data objects.
	 *
	 * @param data
	 *             the new data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}


	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}


	/**
	 * Sets the error.
	 *
	 * @param error
	 *             the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

}
