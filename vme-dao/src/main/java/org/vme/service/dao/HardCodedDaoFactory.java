/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.vme.service.dao.impl.hardcoded.ObservationHarcodedDao;
import org.vme.service.dao.impl.hardcoded.ReferenceHardcodedDao;

/**
 * @author Fabrizio Sibeni
 *
 */
@Alternative
public class HardCodedDaoFactory implements DAOFactory {

	@Inject @Any
	private ReferenceHardcodedDao referenceDAO;
	
	@Inject
	private ObservationHarcodedDao observationDAO;
	
	public HardCodedDaoFactory() {
		super();
	}

//	/* (non-Javadoc)
//	 * @see org.vme.service.dao.DAOFactory#getReferenceConceptProvider()
//	 */
//	@Override
//	@Produces @ConceptProvider public ReferenceConceptProvider<Long> getReferenceConceptProvider() {
//		return this.getReferenceDAO();
//	}
	
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
