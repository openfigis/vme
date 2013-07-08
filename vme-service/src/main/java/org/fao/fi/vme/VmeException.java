package org.fao.fi.vme;

public class VmeException extends RuntimeException {

	public VmeException(Exception e) {
		super(e);
	}

	public VmeException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359907165992410885L;

}
