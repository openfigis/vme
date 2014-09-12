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

	// Marks create() as a producer methods using its return type to determine
	// what type of beans it can produce.
	// The scope of the produced bean, in our case since we want to have only
	// one EntityManagerFactory we mark it as
	// application-scoped
	// @Produces
	// @ApplicationScoped
	// public EntityManagerFactory create() {
	// return
	// Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName());
	// }

	// @Produces
	// @ApplicationScoped
	// public DoubleEntityManager produceDoubleDbProducer() {
	// DoubleEntityManager p = new DoubleEntityManager();
	// p.setEmf(create());
	// p.createNewEm();
	// return p;
	// }

	@Produces
	@VmeDB
	@ApplicationScoped
	public EntityManager produceEntityManager() {
		return Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName()).createEntityManager();
	}

	protected static Logger LOG = LoggerFactory.getLogger(VmeDataBaseProducerApplicationScope.class);

}