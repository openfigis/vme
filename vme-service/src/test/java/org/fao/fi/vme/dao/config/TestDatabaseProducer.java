package org.fao.fi.vme.dao.config;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Alternative
public class TestDatabaseProducer {

	@Produces
	@PersistenceContext(unitName = "test")
	private EntityManager manager;

}
