/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.portal.web.idm.form;


import java.io.Serializable;
import java.util.List;


/**
 * @author mary.jane
 * @since Nov 26, 2018
 */
public class FaceEnrollForm implements Serializable {

	private static final long serialVersionUID = 2978467508258599146L;

	private String image;

	private String staffId;

	private String email;

	private String photoId;

	private String faceId;

	private List<String> icaoCriteria;


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhotoId() {
		return photoId;
	}


	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}


	public String getFaceId() {
		return faceId;
	}


	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}


	public List<String> getIcaoCriteria() {
		return icaoCriteria;
	}


	public void setIcaoCriteria(List<String> icaoCriteria) {
		this.icaoCriteria = icaoCriteria;
	}

}
