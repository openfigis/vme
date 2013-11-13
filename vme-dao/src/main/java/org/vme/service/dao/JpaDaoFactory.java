/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.impl.hibernate.ObservationJpaDao;
import org.vme.service.dao.impl.hibernate.ReferenceJpaDao;
import org.vme.service.dao.sources.figis.FigisDao;


/**
 * @author Fabrizio Sibeni
 *
 */

@Alternative
public class JpaDaoFactory implements DAOFactory {

	@VmeDB
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private FigisDao figisDao;

	@Produces
	private ReferenceDAO referenceDAO = null;
	
	private ObservationDAO observationDAO = null;
	
	
	
	public JpaDaoFactory() {
		
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getReferenceDAO()
	 */
	@Override
	public ReferenceDAO getReferenceDAO() {
		if (referenceDAO==null){
			referenceDAO = new ReferenceJpaDao(this);
		}
		return referenceDAO;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getObservationDAO()
	 */
	@Override
	public ObservationDAO getObservationDAO() {
		if (observationDAO==null){
			observationDAO = new ObservationJpaDao(this);
		}
		return observationDAO;
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
	/**
	 * @return the figisDao
	 */
	public FigisDao getFigisDao() {
		return figisDao;
	}	

}
