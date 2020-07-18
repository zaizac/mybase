package com.util.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author mary.jane
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class LangDesc implements Serializable {

	private static final long serialVersionUID = 8409225214310874187L;

	/** Bangladesh */
	private String bd;

	/** English */
	private String en;

	/** Indonesian */
	private String in;

	/** Malaysia */
	private String my;


	public LangDesc() {
		// DO NOTHING
	}


	public String getBd() {
		return bd;
	}


	public void setBd(String bd) {
		this.bd = bd;
	}


	public String getEn() {
		return en;
	}


	public void setEn(String en) {
		this.en = en;
	}


	public String getIn() {
		return in;
	}


	public void setIn(String in) {
		this.in = in;
	}


	public String getMy() {
		return my;
	}


	public void setMy(String my) {
		this.my = my;
	}

}
