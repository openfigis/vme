/**
 * 
 */
package org.vme.service.dao;



/**
 * @author Fabrizio Sibeni
 *
 */
public interface DAOFactory {
	
	
	public static final int HARDCODED = 1;
	
	public static final int HIBERNATE = 2;
	
	
	
	public static final int CURRENT_FACTORY = HARDCODED;
	
	public ReferenceDAO getReferenceDAO();
	

	public VmeSearchDao getObservationDAO();

//	@Produces @ConceptProvider ReferenceConceptProvider<Long> getReferenceConceptProvider();
}
