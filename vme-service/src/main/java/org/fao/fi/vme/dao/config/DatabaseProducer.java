package org.fao.fi.vme.dao.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DatabaseProducer {

	@Produces
	@PersistenceContext(unitName = "test")
	@TestDatabase
	private EntityManager emPrimary;

	@Produces
	@PersistenceContext(unitName = "production")
	@ProductionDatabase
	private EntityManager emSecondary;

}
