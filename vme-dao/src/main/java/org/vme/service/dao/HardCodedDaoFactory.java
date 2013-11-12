/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;

import org.vme.service.dao.impl.hardcoded.ObservationHarcodedDao;
import org.vme.service.dao.impl.hardcoded.ReferenceHarcodedDao;

/**
 * @author SIBENI
 *
 */

@Alternative
public class HardCodedDaoFactory implements DAOFactory {

	private ReferenceDAO referenceDAO;
	
	private ObservationDAO observationDAO;
	
	public HardCodedDaoFactory() {
		super();
		referenceDAO = new ReferenceHarcodedDao();
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getReferenceDAO()
	 */
	@Override
	public ReferenceDAO getReferenceDAO() {
		if (referenceDAO==null){
			referenceDAO = new ReferenceHarcodedDao();
		}
		return referenceDAO;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getObservationDAO()
	 */
	@Override
	public ObservationDAO getObservationDAO() {
		if (observationDAO==null){
			observationDAO = new ObservationHarcodedDao();
		}
		return observationDAO;
	}

}
