/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.vme.service.dao.impl.hardcoded.ObservationHarcodedDao;
import org.vme.service.dao.impl.hardcoded.ReferenceHarcodedDao;

/**
 * @author SIBENI
 *
 */

@Alternative
public class HardCodedDaoFactory implements DAOFactory {

	@Inject
	private ReferenceHarcodedDao referenceDAO;
	
	@Inject
	private ObservationHarcodedDao observationDAO;
	
	public HardCodedDaoFactory() {
		super();
		referenceDAO = new ReferenceHarcodedDao();
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
		return observationDAO;
	}

}
