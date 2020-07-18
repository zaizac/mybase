/**
 * 
 */
package com.dm.exception;

/**
 * @author mary.jane
 *
 */
public class FileStorageException extends RuntimeException {
	
	private static final long serialVersionUID = 7941697345467286107L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
