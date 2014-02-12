/**
 * 
 */
package org.vme.service.dao;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vme.service.dao.DAOFactory#getObservationDAO()
	 */
	@Override
	public VmeSearchDao getObservationDAO() {
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
