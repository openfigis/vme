/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.impl.jpa.ObservationJpaDao;
import org.vme.service.dao.impl.jpa.ReferenceJpaDao;
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

	@Inject
	private ReferenceJpaDao referenceDAO;
	
	@Inject
	private ObservationJpaDao observationDAO;
	
	
	
	public JpaDaoFactory() {
		
	}
	
	/* (non-Javadoc)
	 * @see org.vme.service.dao.DAOFactory#getReferenceConceptProvider()
	 */
	@Override
	@Produces public ReferenceConceptProvider<Long> getReferenceConceptProvider() {
		return this.getReferenceDAO();
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
