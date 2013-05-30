package org.fao.fi.vme.dao.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Alternative
public class FigisEntityManagerFactoryProducer {

	@Produces
	// Marks create() as a producer methods using its return type to determine what type of beans it can produce.
	@ApplicationScoped
	// The scope of the produced bean, in our case since we want to have only one EntityManagerFactory we mark it as
	// application-scoped
	public EntityManagerFactory create() {
		return Persistence.createEntityManagerFactory("figis-persistence"); // 3
	}

	public void destroy(@Disposes EntityManagerFactory figisEmf) { // 4
		figisEmf.close(); // 5
	}
}
