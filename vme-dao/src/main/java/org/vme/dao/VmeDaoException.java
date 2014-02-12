/**
 * 
 */
package org.vme.dao;

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
	private static final long serialVersionUID = -9180990347485312606L;

	public VmeDaoException(Throwable t) {
		super(t);
	}

	public VmeDaoException(String message) {
		super(message);
	}
	
	public VmeDaoException(String message, Throwable t) {
		super(message, t);
	}
}
