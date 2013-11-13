/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Produces;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface DAOFactory {
	
	
	public static final int HARDCODED = 1;
	
	public static final int HIBERNATE = 2;
	
	
	
	public static final int CURRENT_FACTORY = HARDCODED;
	
	
	@Produces
	public ReferenceDAO getReferenceDAO();
	

	public ObservationDAO getObservationDAO();

	
}
