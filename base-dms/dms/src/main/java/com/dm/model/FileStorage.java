/**
 * 
 */
package com.dm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author mary.jane
 *
 */
@Entity
@Table(name = "DM_FILE_STORAGE")
public class FileStorage {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "FILES_ID")
	private String filesId;

	@Column(name = "LENGTH")
	private long length;

	@Column(name = "FILE_NAME")
	private String filename;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	@Column(name = "VERSION")
	private int version;

	@Column(name = "UPLOAD_DT")
	private Date uploadDate;

	@Lob
	@Column(name = "CONTENT")
	private byte[] content;

	public FileStorage() {
		// DO NOTHING
	}

	/**
	 * @return the filesId
	 */
	public String getFilesId() {
		return filesId;
	}

	/**
	 * @param filesId the filesId to set
	 */
	public void setFilesId(String filesId) {
		this.filesId = filesId;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

}
