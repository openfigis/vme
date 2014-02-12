/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.vme.service.dao.impl.hardcoded.ObservationHarcodedDao;

/**
 * @author Fabrizio Sibeni
 * 
 */
@Alternative
public class HardCodedDaoFactory implements DAOFactory {

	@Inject
	private ObservationHarcodedDao observationDAO;

	public HardCodedDaoFactory() {
		super();
	}

	@Override
	public VmeSearchDao getObservationDAO() {
		return observationDAO;
	}

}
