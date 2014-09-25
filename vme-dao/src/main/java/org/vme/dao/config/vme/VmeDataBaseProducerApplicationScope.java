package org.vme.dao.config.vme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.config.PersistenceUnitConfiguration;

/**
 * 
 * Produces a link to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */

public class VmeDataBaseProducerApplicationScope {

	@Inject
	@VmeDB
	protected PersistenceUnitConfiguration config;

	@Produces
	@VmeDB
	@ApplicationScoped
	public EntityManager produceEntityManager() {
		return Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName()).createEntityManager();
	}

	protected static Logger LOG = LoggerFactory.getLogger(VmeDataBaseProducerApplicationScope.class);

}