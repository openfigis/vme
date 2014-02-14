package org.vme.dao.config.figis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	static final private Logger LOG = LoggerFactory.getLogger(FigisDataBaseProducer.class);

	static int i = 0;

	private static EntityManagerFactory factory;

	@Produces
	// Marks create() as a producer methods using its return type to determine
	// what type of beans it can produce.
	@ApplicationScoped
	// The scope of the produced bean, in our case since we want to have only
	// one EntityManagerFactory we mark it as
	// application-scoped
	public EntityManagerFactory create() {
		if (factory == null) {
			LOG.debug("=======Persistence.createEntityManagerFactory(figis-persistence)===");
			factory = Persistence.createEntityManagerFactory("figis-persistence"); // 3
		}
		return factory;
	}

	@Produces
	@FigisDB
	public EntityManager produceEntityManager() {
		LOG.debug("======= create().createEntityManager()===");
		return create().createEntityManager();
	}

}