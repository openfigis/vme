/**
 * 
 */
package org.vme.dao;



/**
 * @author Fabrizio Sibeni
 *
 */
public class ReferenceServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4431083184702644098L;

	/**
	 * 
	 */
	public ReferenceServiceException() {
	}

	/**
	 * @param message
	 */
	public ReferenceServiceException(String message) {
		super(message);
	}

	/**
	 * @param t
	 */
	public ReferenceServiceException(Throwable t) {
		super(t);
	}

	/**
	 * @param message
	 * @param t
	 */
	public ReferenceServiceException(String message, Throwable t) {
		super(message, t);
	}

}
