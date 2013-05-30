package org.fao.fi.vme.dao.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Alternative
public class VmeEntityManagerFactoryProducer {

	@Produces
	// Marks create() as a producer methods using its return type to determine what type of beans it can produce.
	@ApplicationScoped
	// The scope of the produced bean, in our case since we want to have only one EntityManagerFactory we mark it as
	// application-scoped
	public EntityManagerFactory create() {

		// Properties p = new Properties();
		// p.put("eclipselink.persistencexml", "/org/acme/acme-persistence.xml");
		// EntityManagerFactory factory = Persistence.createEntityManagerFactory("acme", p);

		return Persistence.createEntityManagerFactory("vme-persistence");
	}

	public void destroy(@Disposes EntityManagerFactory vmeEmf) {
		vmeEmf.close();
	}
}
