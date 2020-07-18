/**
 * 
 */
package com.dm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mary.jane
 *
 */
@Entity
@Table(name = "DM_FILE_HISTORY")
public class FileHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FILE_HISTORY_ID", unique = true, nullable = false)
	private Integer historyId;

	@Column(name = "DOC_MGT_ID")
	private String docMgtId;

	@Column(name = "FILES_ID")
	private String filesId;

	@Column(name = "VERSION")
	private int version;

	public FileHistory() {
		// DO NOTHING
	}

	/**
	 * @return the historyId
	 */
	public Integer getHistoryId() {
		return historyId;
	}

	/**
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	/**
	 * @return the docMgtId
	 */
	public String getDocMgtId() {
		return docMgtId;
	}

	/**
	 * @param docMgtId the docMgtId to set
	 */
	public void setDocMgtId(String docMgtId) {
		this.docMgtId = docMgtId;
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

}
