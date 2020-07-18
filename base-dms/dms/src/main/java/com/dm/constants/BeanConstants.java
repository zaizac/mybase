/**
 * Copyright 2019. 
 */
package com.dm.constants;


/**
 * The Class BeanConstants.
 *
 * @author Mary Jane Buenaventura
 * @since May 8, 2018
 */
public final class BeanConstants {

	/**
	 * Instantiates a new bean constants.
	 */
	private BeanConstants() {
		throw new IllegalStateException("BeanConstants Utility class");
	}


	/** The Constant BEAN_SCOPE_SINGLETON. */
	public static final String BEAN_SCOPE_SINGLETON = "singleton";

	/** The Constant BEAN_SCOPE_PROTOTYPE. */
	public static final String BEAN_SCOPE_PROTOTYPE = "prototype";

	/** The Constant DOCUMENT_REPO. */
	public static final String DOCUMENT_REPO = "documentsDao";

	/** The Constant DOCUMENT_SVC. */
	public static final String DOCUMENT_SVC = "documentsService";

	/** The Constant DOCUMENT_PROCESSOR. */
	public static final String DOCUMENT_PROCESSOR = "documentsProcessor";

	/** The Constant CONTAINER_REPO. */
	public static final String CONTAINER_REPO = "containerDao";

	/** The Constant CONTAINER_SVC. */
	public static final String CONTAINER_SVC = "containerService";

	/** The Constant CONTAINER_PROCESSOR. */
	public static final String CONTAINER_PROCESSOR = "containerProcessor";

	/** The Constant STORAGE_SVC. */
	public static final String STORAGE_SVC = "storageService";

}