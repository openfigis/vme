package org.vme.dao.config.vme;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

	// Marks create() as a producer methods using its return type to determine
	// what type of beans it can produce.
	// The scope of the produced bean, in our case since we want to have only
	// one EntityManagerFactory we mark it as
	// application-scoped
	@Produces
	@ApplicationScoped
	public EntityManagerFactory create() {

		return Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName());
	}

	@Produces
	@VmeDB
	@ApplicationScoped
	public EntityManager produceEntityManager() {
		return create().createEntityManager();
	}

	protected static Logger LOG = LoggerFactory.getLogger(VmeDataBaseProducerApplicationScope.class);

}