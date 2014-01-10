/**
 * 
 */
package org.vme.service.dao;

/**
 * Generic vme runtime exception. Vme so far does not have checked exceptions,
 * the idea behind is that checked exceptions create a lot of ugly boilerplate
 * code, and more important, all code should be tested so that exceptions will
 * not occur runtime.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9180990347485312606L;

	public VmeDaoException(Exception e) {
		super(e);
	}

	public VmeDaoException(String message) {
		super(message);
	}

}
