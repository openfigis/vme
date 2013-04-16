package org.fao.fi.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.CdiRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class JPATest {

	@Inject
	private EntityManager manager;

	@BeforeClass
	public static void setup() {
		// redirect logging to avoid file pollution
		System.setProperty("derby.stream.error.field", "java.lang.System.out");
		System.setProperty("org.slf4j.simpleLogger.log.openjpa.jdbc.SQL", "trace");

	}

	@Test
	public void roundTrip() {
		Vme created = new Vme();
		String status = "go";
		created.setStatus(status);

		store(created);

		Vme loaded = lookup(1, Vme.class);

		assertNotNull(loaded);

		assertEquals(1, loaded.getId());
		assertEquals(status, loaded.getStatus());

	}

	void store(Object entity) {

		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();
		manager.close();
	}

	<T> T lookup(Object id, Class<T> type) {

		manager.getTransaction().begin();
		T entity = manager.find(type, id);
		manager.getTransaction().commit();
		manager.close();

		return entity;
	}

}
