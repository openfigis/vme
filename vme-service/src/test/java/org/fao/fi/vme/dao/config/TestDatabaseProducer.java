package org.fao.fi.vme.dao.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TestDatabaseProducer {

	@Produces
	@PersistenceContext(unitName = "test")
	private EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	};

}
