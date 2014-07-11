package org.fao.fi.vme;

public class FactSheetChangeException extends RuntimeException {

	public FactSheetChangeException(Exception e){
		super(e);
	}
	
	public FactSheetChangeException(String message) {
		super(message);
	}
	
	public FactSheetChangeException(String message, Exception e){
		super(message, e);
	}
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 16546844665232L;

}
