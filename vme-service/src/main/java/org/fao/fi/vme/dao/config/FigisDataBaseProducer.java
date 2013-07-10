package org.fao.fi.vme.dao.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * Produces the link to the figis DB.
 * 
 * TODO investigate whether this one should be moved to vme-configuration.
 * 
 * @author Erik van Ingen
 * 
 */
@Alternative
public class FigisDataBaseProducer {

	// private static EntityManagerFactory factory;

	@Produces
	// Marks create() as a producer methods using its return type to determine what type of beans it can produce.
	@ApplicationScoped
	// The scope of the produced bean, in our case since we want to have only one EntityManagerFactory we mark it as
	// application-scoped
	public EntityManagerFactory create() {
		return Persistence.createEntityManagerFactory("figis-persistence"); // 3
	}

	@Produces
	@FigisDB
	public EntityManager produceEntityManager() {
		return create().createEntityManager();
	}

}