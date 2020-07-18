/**
 * 
 */
package com.dm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author mary.jane
 *
 */
@Entity
@Table(name = "DM_FILE_METADATA")
public class FileMetadata {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "DOC_MGT_ID")
	private String id;

	@Column(name = "REF_NO")
	private String refno;

	@Column(name = "DOC_ID")
	private String docid;

	@Column(name = "TRXN_NO")
	private String txnno;

	@Column(name = "PROJ_ID")
	private String projId;
	
	@Column(name = "SYSTEM_TYPE")
	private String systemType;

	@Column(name = "FILES_ID")
	private String filesId;

	@Column(name = "FILE_NAME")
	private String filename;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	@Column(name = "LENGTH")
	private long length;

	@Column(name = "VERSION")
	private int version;

	@Column(name = "UPLOAD_DT")
	private Date uploadDate;

	@Transient
	private byte[] content;

	@Transient
	private List<FileMetadata> history;

	public FileMetadata() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the refno
	 */
	public String getRefno() {
		return refno;
	}

	/**
	 * @param refno the refno to set
	 */
	public void setRefno(String refno) {
		this.refno = refno;
	}

	/**
	 * @return the docid
	 */
	public String getDocid() {
		return docid;
	}

	/**
	 * @param docid the docid to set
	 */
	public void setDocid(String docid) {
		this.docid = docid;
	}
 
	/**
	 * @return the txnno
	 */
	public String getTxnno() {
		return txnno;
	}

	/**
	 * @param txnno the txnno to set
	 */
	public void setTxnno(String txnno) {
		this.txnno = txnno;
	}

	/**
	 * @return the projId
	 */
	public String getProjId() {
		return projId;
	}

	/**
	 * @param projId the projId to set
	 */
	public void setProjId(String projId) {
		this.projId = projId;
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

	/**
	 * @return the history
	 */
	public List<FileMetadata> getHistory() {
		return history;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(List<FileMetadata> history) {
		this.history = history;
	}

	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	
}
