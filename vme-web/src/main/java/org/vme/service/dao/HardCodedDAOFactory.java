/**
 * 
 */
package org.vme.service.dao;

import org.vme.service.hardcoded.impl.ReferenceDAOHarcoded;

/**
 * @author SIBENI
 *
 */
public class HardCodedDAOFactory extends DAOFactory {

	private ReferenceDAOHarcoded referenceDAO;
	
	public HardCodedDAOFactory() {
		super();
		referenceDAO = new ReferenceDAOHarcoded();
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getReferenceDAO()
	 */
	@Override
	public ReferenceDAO getReferenceDAO() {
		return referenceDAO;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getObservationDAO()
	 */
	@Override
	public ObservationDAO getObservationDAO() {
		return null;
	}

}
