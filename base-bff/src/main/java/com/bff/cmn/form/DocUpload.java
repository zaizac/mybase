package com.bff.cmn.form;

import java.io.Serializable;

import com.bff.cmn.form.FileUpload;

/**
 * @author Mubarak
 * @since Feb 12, 2019
 */

public class DocUpload implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5526697934909258224L;
	
	
	private FileUpload fileUpload;
	
	public DocUpload() {
		super();
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}


}
