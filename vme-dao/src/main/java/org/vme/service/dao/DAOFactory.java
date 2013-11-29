/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Produces;

import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider;


/**
 * @author Fabrizio Sibeni
 *
 */
public interface DAOFactory {
	
	
	public static final int HARDCODED = 1;
	
	public static final int HIBERNATE = 2;
	
	
	
	public static final int CURRENT_FACTORY = HARDCODED;
	
	public ReferenceDAO getReferenceDAO();
	

	public ObservationDAO getObservationDAO();

	@Produces ReferenceConceptProvider<Long> getReferenceConceptProvider();
}
