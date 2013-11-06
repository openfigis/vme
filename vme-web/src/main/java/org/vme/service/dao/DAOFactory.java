/**
 * 
 */
package org.vme.service.dao;

/**
 * @author Fabrizio Sibeni
 *
 */
public abstract class DAOFactory {
	
	
	public static final int HARDCODED = 1;
	
	public static final int HIBERNATE = 2;
	
	
	
	public static final int CURRENT_FACTORY = HARDCODED;
	
	
	
	public abstract ReferenceDAO getReferenceDAO();
	

	public abstract ObservationDAO getObservationDAO();

	
	
	public static DAOFactory getDaoFactory (int whichFactory) {
		switch (whichFactory) {
		case HARDCODED:
			return new HardCodedDAOFactory();
			
		case HIBERNATE:
			return new HibernateDAOFactory();	
		default:
			return null;
		}
		
		
	}
	
}
