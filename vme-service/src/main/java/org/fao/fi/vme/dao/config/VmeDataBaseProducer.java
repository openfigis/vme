package org.fao.fi.vme.dao.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * Produces a link to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */
@Alternative
public class VmeDataBaseProducer {

	// private static EntityManagerFactory factory;

	@Produces
	// Marks create() as a producer methods using its return type to determine what type of beans it can produce.
	@ApplicationScoped
	// The scope of the produced bean, in our case since we want to have only one EntityManagerFactory we mark it as
	// application-scoped
	public EntityManagerFactory create() {
		// if (factory == null) {
		// factory = Persistence.createEntityManagerFactory("vme-persistence");
		// }
		// return factory;
		return Persistence.createEntityManagerFactory("vme-persistence");
	}

	@Produces
	@VmeDB
	public EntityManager produceEntityManager() {
		return create().createEntityManager();
	}

}