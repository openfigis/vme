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
@ApplicationScoped
public class VmeDataBaseProducerApplicationScope {
	protected static Logger LOG = LoggerFactory.getLogger(VmeDataBaseProducerApplicationScope.class);

	private static DoubleEntityManager p;

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
	@ApplicationScoped
	public DoubleEntityManager produceDoubleDbProducer() {
		// Intervention Erik van Ingen, 3 September 2014. For some reason,
		// @ApplicationScoped is not working correctly. I did
		// investigate, but I could not find a solution. Therefore this
		// workaround. It looks like that the @ApplicationScoped on class level
		// works correctly, but on method level it is called more than once.
		if (p == null) {
			System.out.println("===============produceDoubleDbProducer, should only appear once. ");
			LOG.info("===============produceDoubleDbProducer, should only appear once. ");
			p = new DoubleEntityManager();
			p.setEmf(create());
			p.createNewEm();
		}
		return p;
	}

	@Produces
	@VmeDB
	public EntityManager produceEntityManager() {
		return produceDoubleDbProducer().getEm();
	}

}